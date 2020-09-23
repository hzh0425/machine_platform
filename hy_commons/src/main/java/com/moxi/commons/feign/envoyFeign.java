package com.moxi.commons.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 21:46
 */
@Service
@FeignClient("machine-hy-envoy")
public interface envoyFeign {
    //增加xds服务发现
    @PostMapping("/v2/add")
    public boolean add(@RequestParam("proxyId")String proxy, @RequestParam("cluster_ip")String cluster, @RequestParam("port")int port);
    //去除xds服务发现
    @PostMapping("/v2/delete")
    public boolean delete(@RequestParam("proxyId")String proxy);
}
