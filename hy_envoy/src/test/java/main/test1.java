package main;

import main.server.XdsServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 20:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test1 {
    @Autowired
    XdsServer server;
    @Test
    public void test1(){
        String cluster_ip="127.0.0.1";
        String proxy="asoigjfqojit";
        int port=8013;
        server.AddGrpcWebVirtualHostRoute(proxy,cluster_ip,port);
        System.out.println(server.GrpcRouteConfig);
        System.out.println("success");
        System.out.println(server.GrpcClusterConfig);
    }
}
