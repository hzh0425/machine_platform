package com.moxi.kube.util;


import com.moxi.kube.global.SysConf;
import com.moxi.xo.vo.DeploymentServiceVO;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/15 21:20
 */
@Component
public class KubeUtil {

    private ApiClient apiClient;
    private AppsV1Api appsV1Api;
    private CoreV1Api coreV1Api;



    @PostConstruct
    public void init() throws Exception {
        ClassPathResource resource=new ClassPathResource("kube.config");
        InputStream stream=resource.getInputStream();
        FileOutputStream outputStream=new FileOutputStream("temp.config");
        byte[]bytes=new byte[1024];
        int read=0;
        while((read=stream.read(bytes))!=-1){
            outputStream.write(bytes,0,read);
        }
        System.out.println("success");
        apiClient=ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("temp.config")))
                                .setVerifyingSsl(false).build();
        Configuration.setDefaultApiClient(apiClient);
        appsV1Api=new AppsV1Api(apiClient);
        coreV1Api=new CoreV1Api(apiClient);
    }

    /**
     * 部署微服务
     * @return
     */
    public Map<String, Object> DeployMicroService(DeploymentServiceVO service){
        // 镜像指定从私有仓库中获取，避免外部镜像安全问题
        if(!service.image_name.trim().contains(SysConf.registry)){
            service.image_name=SysConf.registry+SysConf.FILE_SEGMENTATION+service.image_name.trim();
        }
        //选择标签
        //1.deployment模板
        V1Deployment deployment=buildDeployment(service);
        //2.service模板
        V1Service v1Service=buildService(service);
        System.out.println(v1Service);
        //请求创建服务
        Map<String,Object>result=new HashMap<>();
        try {
            V1Deployment namespacedDeployment = appsV1Api.createNamespacedDeployment(SysConf.NAMESPACE, deployment, null, null, null);
            V1Service namespacedService = coreV1Api.createNamespacedService(SysConf.NAMESPACE, v1Service, null, null, null);
            service.cluster_ip=namespacedService.getSpec().getClusterIP();
            service.NodePort=namespacedService.getSpec().getPorts().get(0).getNodePort();
            result.put("service",service);
            result.put("code","success");
        }catch (ApiException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
        }
        //相应结果
        return result;
    }


    /**
     * 创建deployment
     * @param service
     * @return
     */
    private V1Deployment buildDeployment(DeploymentServiceVO service) {
        Map<String,String> selectLabels=new HashedMap();
        selectLabels.put(SysConf.APP,SysConf.SVC+service.deployment_id.toLowerCase());
        String name=SysConf.SVC+service.deployment_id.toLowerCase();
        //container envList
        ArrayList<V1EnvVar> envList=new ArrayList<>();
        if(service.envList!=null&&service.envList.size()>0){
            service.envList.forEach(item->{
                V1EnvVar envVar=new V1EnvVarBuilder()
                        .withName(item.name)
                        .withValue(item.value)
                        .build();
                envList.add(envVar);
            });
        }

        //container容器
        ArrayList<V1ContainerPort> portArrayList=new ArrayList<>();
        if(service.portList!=null&&service.portList.size()>0){
            service.portList.forEach(item->{
                V1ContainerPort port=new V1ContainerPort();
                port.setContainerPort(item.getPort());
                portArrayList.add(port);
            });
        }
        ArrayList<V1Container> containerList=new ArrayList<>();
        V1Container container=new V1ContainerBuilder()
                .withName(name)
                .withImage(service.image_name)
                .withImagePullPolicy(SysConf.IMAGE_PullPolicy)
                .withEnv(envList)
                .withPorts(portArrayList)
                .build();
        containerList.add(container);

        //deployment模板
        V1Deployment deployment=new V1DeploymentBuilder()
                .withApiVersion(SysConf.API_VERSION)
                .withKind(SysConf.TYPE_DEPLOYMENT)
                //元数据
                .withMetadata(
                        new V1ObjectMetaBuilder().
                                withName(name).
                                withNamespace(SysConf.NAMESPACE)
                                .build()
                )
                //详细信息
                .withSpec(
                        new V1DeploymentSpecBuilder()
                                //副本数
                                .withReplicas(service.replicas)
                                //选择匹配器
                                .withSelector(
                                        new V1LabelSelectorBuilder()
                                                .withMatchLabels(selectLabels)
                                                .build()
                                )
                                //pod模板
                                .withTemplate(
                                        new V1PodTemplateSpecBuilder()
                                                //pod 元数据
                                                .withMetadata(
                                                        new V1ObjectMetaBuilder()
                                                                .withLabels(selectLabels)
                                                                .build()
                                                )
                                                .withSpec(
                                                        new V1PodSpecBuilder()
                                                                .withContainers(
                                                                        containerList
                                                                ).build()
                                                ).build()
                                ).build()
                ).build();
        return deployment;
    }

    /**
     * 创建service
     */
    public V1Service buildService(DeploymentServiceVO service){
        Map<String,String> selectLabels=new HashedMap();
        selectLabels.put(SysConf.APP,SysConf.SVC+service.deployment_id.toLowerCase());
        String name=SysConf.SVC+service.deployment_id.toLowerCase();
        //3.1portList

        ArrayList<V1ServicePort> servicePorts=new ArrayList<>();
        if(service.portList.size()>0){
            service.portList.forEach(item->{
                V1ServicePort port=new V1ServicePort();
                port.setPort(item.port);
                //为什么加了targetport之后就不行了??????
                //port.setTargetPort(item.cluster_port);
                servicePorts.add(port);
            });
        }
        //3.2sessionAffinityConfig
        V1SessionAffinityConfig config=service.session_affinity == 0 ? null : new V1SessionAffinityConfigBuilder()
                .withClientIP(
                        new V1ClientIPConfigBuilder()
                                .withTimeoutSeconds(10800)
                                .build()
                ).build();
        //3.3 service
        V1Service v1Service=new V1ServiceBuilder()
                .withApiVersion(SysConf.SERVICE_VERSION)
                .withKind(SysConf.TYPE_SERVICE)
                .withMetadata(
                        new V1ObjectMetaBuilder()
                                .withLabels(selectLabels)
                                .withName(name)
                                .withNamespace(SysConf.NAMESPACE)
                                .build()
                )
                .withSpec(
                        new V1ServiceSpecBuilder()
                                .withType(SysConf.TYPE_NODEPORT)
                                .withSelector(selectLabels)
                                .withSessionAffinity(service.session_affinity == 0 ? null : "ClientIP")
                                .withSessionAffinityConfig(config)
                                .withPorts(servicePorts)
                                .build()
                ).build();
        return v1Service;
    }

    /**
     * 移除部署
     */
    @Async
    public Boolean RemoveMicroDeployment(String deployment_id) throws ApiException {
        String dId=SysConf.SVC+deployment_id;
        V1Status status=appsV1Api.deleteNamespacedDeployment(dId,SysConf.NAMESPACE,null,null,null,null,null,null);
        V1Status status1=coreV1Api.deleteNamespacedService(dId,SysConf.NAMESPACE,null,null,null,null,null,null);
        if(status1.getStatus()=="Success"){
            return true ;
        }else{
            return false;
        }
    }

    /**
     * 获取某个命名空间下容器的状态
     * @return
     */
    public java.util.List<V1Pod> getPodStatus(String namespace) throws ApiException {
        V1PodList list=coreV1Api.listNamespacedPod(namespace,null,null,null,null,null,null,null,null,null);
        return list.getItems();
    }

}
