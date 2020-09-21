package main.controller;

import main.entity.EnvoyClustersConfig;
import main.entity.EnvoyRouteConfig;
import main.server.XdsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/discovery:routes")
    public EnvoyRouteConfig RDSDiscoveryServer(){
       return xdsServer.GrpcRouteConfig;
    }
    @GetMapping("/discovery:clusters")
    public EnvoyClustersConfig CDSDiscoveryServer(){
        return xdsServer.GrpcClusterConfig;
    }



}
