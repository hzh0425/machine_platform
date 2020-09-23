package main.server;

import com.alibaba.fastjson.JSON;
import main.bean.XdsCluster;
import main.bean.XdsListener;
import main.entity.*;
import main.entity.listener.EnvoyFilter;
import main.entity.listener.EnvoyFilterConfig;
import main.entity.listener.EnvoyListener;
import main.entity.listener.EnvoyListenerConfig;
import main.global.SysConf;
import main.mapper.XdsClusterMapper;
import main.mapper.XdsListenerMapper;
import main.util.RedissLockUtil;
import main.util.XdsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 20:01
 */
@Service
public class XdsServer {
    @Resource
    XdsClusterMapper clusterMapper;
    @Autowired
    RedissLockUtil lockUtil;
    @Autowired
    XdsUtil xdsUtil;
    @Resource
    XdsListenerMapper listenerMapper;

   AtomicInteger integer=new AtomicInteger(0);

   public  EnvoyClustersConfig GrpcClusterConfig;
   public EnvoyListenerConfig GrpcLinsterConfig;

   @PostConstruct
   public void init(){
       this.GrpcClusterConfig=initClusterConfig();
       this.GrpcLinsterConfig=initListenerConfig();
   }

    /**
     * 初始化cluster config
     * @return
     */
   public EnvoyClustersConfig initClusterConfig(){
       //从数据库获取最大的版本
       EnvoyClustersConfig config=null;
       XdsCluster cluster=clusterMapper.getMaxVersionCluster();
       if(cluster!=null){
           String json=cluster.getJson();
           config= JSON.parseObject(json,EnvoyClustersConfig.class);
       }else{
           config=new EnvoyClustersConfig("0",new ArrayList<EnvoyCluster>());
       }
       return config;
   }
   public EnvoyListenerConfig initListenerConfig(){
       EnvoyListenerConfig config=null;
       XdsListener listener=listenerMapper.getMaxVersionListener();
       if(listener!=null){
           String json=listener.getJson();
           config=JSON.parseObject(json,EnvoyListenerConfig.class);
       }else{
           config=new EnvoyListenerConfig("0",new ArrayList<EnvoyListener>());
       }
       return config;
   }
    /**
     * 增加代理
     */
    public boolean AddGrpcWebVirtualHostRoute(String proxy_id,String cluster_ip,int port){
        //1.先尝试获取锁
        if(RedissLockUtil.tryLock(SysConf.INCRE_VERSION, TimeUnit.SECONDS,10,10)){
            EnvoyCluster clusterItem=null;
            EnvoyListener listener=null;
            String version=integer.getAndAdd(1)+"";
            this.GrpcLinsterConfig.version_info=version;
            this.GrpcClusterConfig.version_info=version;
            //构建route
            clusterItem=xdsUtil.buildClusterItem(proxy_id,cluster_ip,port);
            listener= xdsUtil.buildListenerConfig(proxy_id,cluster_ip,port);
            this.GrpcLinsterConfig.resources.add(listener);
            this.GrpcClusterConfig.resources.add(clusterItem);
            //保存到mysql
            saveConfigJsonToMysql(version);
            return true;
        }else return false;
    }
    @Async
    public boolean saveConfigJsonToMysql(String version){
        //1.cluster
        XdsCluster cluster=new XdsCluster();
        cluster.setJson(JSON.toJSONString(this.GrpcClusterConfig));
        cluster.setVersion(Integer.parseInt(version));
        cluster.setCreate_date(new Date());
        cluster.insert();
        RedissLockUtil.unlock(SysConf.INCRE_VERSION);
        //2.listener
        XdsListener xdsListener=new XdsListener();
        xdsListener.setCreate_date(new Date());
        xdsListener.setJson(JSON.toJSONString(this.GrpcLinsterConfig));
        xdsListener.setVersion(Integer.parseInt(version));
        xdsListener.insert();
        return true;
    }

}
