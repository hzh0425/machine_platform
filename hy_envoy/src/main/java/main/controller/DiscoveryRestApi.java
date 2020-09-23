package main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.swagger.annotations.Api;
import main.bean.XdsCluster;
import main.entity.EnvoyClustersConfig;
import main.entity.listener.EnvoyListenerConfig;
import main.mapper.xdsMapper;
import main.server.XdsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 21:24
 */
@RestController
@RequestMapping("/v2")
@Api(value = "envoy服务发现相关接口", tags = {"envoy服务发现相关接口"})
public class DiscoveryRestApi {
    static{
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    @Autowired
    XdsServer xdsServer;

    @PostMapping("/discovery:listeners")
    public EnvoyListenerConfig LDSDiscoveryServer(){
       return xdsServer.GrpcLinsterConfig;
    }
    @PostMapping("/discovery:clusters")
    public EnvoyClustersConfig CDSDiscoveryServer(){
        return xdsServer.GrpcClusterConfig;
    }


    @PostMapping("/add")
    public boolean add(@RequestParam("proxyId")String proxy, @RequestParam("cluster_ip")String cluster, @RequestParam("port")int port){
        return xdsServer.AddGrpcWebProxy(proxy,cluster,port);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam("proxyId")String proxy){
        return xdsServer.deleteGrpcWebProxy(proxy);
    }


}
