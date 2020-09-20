package com.moxi.kube.restApi;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 14:10
 */
@RestController
@RequestMapping("/proxy")
@Api(value = "代理相关接口", tags = {"代理相关接口"})
public class proxyRestApi {
}
