package com.moxi.commons.feign;

import com.moxi.commons.config.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hzh
 * @since 2020-08-07
 */

@FeignClient(name = "hy-admin",configuration = FeignConfiguration.class)
public interface AdminFeignClient {

}