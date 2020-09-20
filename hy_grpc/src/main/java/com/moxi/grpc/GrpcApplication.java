package com.moxi.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/12 16:30
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.moxi.grpc.client",
        "com.moxi.grpc.util",
        "com.moxi.grpc.restApi",
        "com.moxi.grpc.bean",
        "com.moxi.grpc.service",
        "com.moxi.grpc.multiThread"
})
public class GrpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class,args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //配置允许跨域访问的路径
                registry.addMapping("/**/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders("")
                        .maxAge(3600);
            }
        };
    }
}

