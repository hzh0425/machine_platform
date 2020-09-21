package main.controller;

import main.entity.EnvoyClustersConfig;
import main.entity.EnvoyRouteConfig;
import main.server.XdsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 21:24
 */
@RestController
@RequestMapping("/v2")
public class envoyController {
    @Autowired
    XdsServer xdsServer;
    @PostMapping("/discovery:routes")
    public EnvoyRouteConfig RDSDiscoveryServer(){
       return xdsServer.GrpcRouteConfig;
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


}
