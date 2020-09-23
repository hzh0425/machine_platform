package com.moxi.kube.feign;

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
    @PostMapping("/v2/add")
    public String add(@RequestParam("proxy")String proxy, @RequestParam("cluster")String cluster, @RequestParam("port")int port);

}
