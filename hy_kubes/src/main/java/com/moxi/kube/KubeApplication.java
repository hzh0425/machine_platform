package com.moxi.kube;

import org.checkerframework.checker.units.qual.K;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/15 21:17
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.moxi.kube.server",
        "com.moxi.kube.config",
        "com.moxi.kube.restApi",
        "com.moxi.kube.util",
        "com.moxi.xo.utils",
        "com.moxi.xo.service",
        "com.moxi.commons.config",
        "com.moxi.utils"
})
public class KubeApplication {
    public static void main(String[] args) {
        SpringApplication.run(KubeApplication.class,args);
    }
}
