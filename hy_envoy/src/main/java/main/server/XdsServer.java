package main.server;

import com.alibaba.fastjson.JSON;
import main.bean.XdsCluster;
import main.entity.*;
import main.entity.listener.EnvoyFilter;
import main.entity.listener.EnvoyFilterConfig;
import main.entity.listener.EnvoyListener;
import main.entity.listener.EnvoyListenerConfig;
import main.global.SysConf;
import main.mapper.XdsClusterMapper;
import main.util.RedissLockUtil;
import main.util.XdsUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
   AtomicInteger integer=new AtomicInteger(0);
   public  EnvoyClustersConfig GrpcClusterConfig;
   public EnvoyListenerConfig GrpcLinsterConfig;
   public List<EnvoyListener> envoyListenerList;
   public List<EnvoyRoute> envoyRouteList;
   public final static String route_type="type.googleapis.com/envoy.api.v2.RouteConfiguration";
   @PostConstruct
   public void init(){
       this.envoyListenerList=new ArrayList<>();
       this.GrpcClusterConfig=initClusterConfig();
       this.GrpcLinsterConfig=initListenerConfig();
   }

    /**
     * 初始化route config
     * @return
     */
   public EnvoyClustersConfig initClusterConfig(){
       //从数据库获取最大的版本
       XdsCluster cluster=clusterMapper.getMaxVersionCluster();
       String json=cluster.getJson();
       EnvoyClustersConfig config= JSON.parseObject(json,EnvoyClustersConfig.class);
       return config;
   }
   public EnvoyListenerConfig initListenerConfig(){
       return new EnvoyListenerConfig("0",envoyListenerList);
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
            this.envoyListenerList.add(listener);
            this.GrpcClusterConfig.resources.add(clusterItem);
            //保存到mysql
            //1.cluster
            XdsCluster cluster=new XdsCluster();
            cluster.setJson(JSON.toJSONString(this.GrpcClusterConfig));
            cluster.setVersion(Integer.parseInt(version));
            cluster.setCreate_date(new Date());
            cluster.insert();
            RedissLockUtil.unlock(SysConf.INCRE_VERSION);
            //2.listener

            return true;
        }else return false;
    }

}
