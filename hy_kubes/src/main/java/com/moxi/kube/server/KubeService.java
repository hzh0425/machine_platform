package com.moxi.kube.server;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.commons.entity.MicroDeployment;
import com.moxi.kube.global.SqlConf;
import com.moxi.kube.util.KubeUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.MicroDeploymentService;
import com.moxi.xo.vo.DeploymentServiceVO;
import com.moxi.xo.vo.MicroDeploymentStatus;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 0:29
 */
@Service
public class KubeService {
    @Autowired
    KubeUtil kubeUtil;
    @Autowired
    MicroDeploymentService deploymentService;
    public String DeployMicroService(DeploymentServiceVO service){
        //1.部署服务
        Map<String,Object> deployResult=kubeUtil.DeployMicroService(service);
        //2.判断,构建entity,插入数据库
        String code=deployResult.get(SysConf.CODE).toString();
        if(code.equals(SysConf.SUCCESS)){
            if(service.getPortList()!=null){
                service.ports= JSON.toJSONString(service.getPortList());
            }
            if(service.getEnvList()!=null){
                service.env=JSON.toJSONString(service.getEnvList());
            }
            System.out.println("service-------");
            System.out.println(service);
            MicroDeployment deployment=new MicroDeployment(service.getDeployment_id(),service.getDescription(),service.getName(),new Date(),service.getImage_name(),service.getReplicas(),service.getPorts(),service.getNamespace(),service.getEnv(),1,service.getSession_affinity(),service.getCommand(),service.getCluster_ip(),service.getOwner(),service.getNodePort());
            deployment.insert();
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.INSERT_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR, MessageConf.INSERT_FAIL);
        }
    }

    public String deleteMicroService(String deployment_id) throws ApiException {
        //查看mysql中是否有该记录
        QueryWrapper<MicroDeployment> wrapper=new QueryWrapper<>();
        wrapper.eq(SqlConf.DEPLOYMENT_ID,deployment_id);
        MicroDeployment deployment=deploymentService.getOne(wrapper);
        if(deployment==null)return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        //删除微服务部署
        kubeUtil.RemoveMicroDeployment(deployment_id);
        //删除mysql记录
        deployment.deleteById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
    }

    public List<MicroDeployment> getList(){
        return deploymentService.list();
    }

    public String getPodStatus() throws ApiException {
//        List<MicroDeployment> list=deploymentService.list();
//        List<String> nameList=list.stream().map(x-> com.moxi.kube.global.SysConf.SVC+x.getDeploymentId()).collect(Collectors.toList());;
//        List<V1Pod> podList=kubeUtil.getPodStatus(com.moxi.kube.global.SysConf.NAMESPACE);
//        Map<String,Boolean> filterResult=podList.stream().filter(x->{
//            return nameList.contains(x.getMetadata().getName());
//        });
//        return ResultUtil.result(SysConf.SUCCESS,filterResult);
        return "success";
    }
}
