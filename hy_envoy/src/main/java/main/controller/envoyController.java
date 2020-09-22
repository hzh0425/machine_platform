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
@Api("代理相关接口")
public class envoyController {
    static{
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    @Autowired
    XdsServer xdsServer;
    @Resource
    xdsMapper xdsMapper;
    @PostMapping("/discovery:listeners")
    public EnvoyListenerConfig LDSDiscoveryServer(){
       return xdsServer.GrpcLinsterConfig;
    }
    @PostMapping("/discovery:clusters")
    public EnvoyClustersConfig CDSDiscoveryServer(){
        return xdsServer.GrpcClusterConfig;
    }

    @PostMapping("/add")
    public String add(@RequestParam("proxy")String proxy, @RequestParam("cluster")String cluster, @RequestParam("port")int port){
        xdsServer.AddGrpcWebVirtualHostRoute(proxy,cluster,port);
        return "success";
    }

    @GetMapping("/test")
    public EnvoyClustersConfig test(){
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        XdsCluster cluster=xdsMapper.getMaxVersionCluster();
        String json=cluster.getJson();
        System.out.println(json);
        EnvoyClustersConfig config= JSON.parseObject(json,EnvoyClustersConfig.class);
        return config;
    }


}
