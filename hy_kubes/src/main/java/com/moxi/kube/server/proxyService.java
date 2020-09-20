package com.moxi.kube.server;

import org.springframework.stereotype.Service;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 14:14
 */
@Service
public class proxyService {
    /**
     * 创建grpc代理服务
     */
    public String CreateGrpcWebProxy(String deployment_id,int port){
        return "success";
    }

}
