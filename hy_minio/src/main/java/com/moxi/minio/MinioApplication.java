package com.moxi.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/8 19:48
 */
@ComponentScan(basePackages = {
        "com.moxi.minio.config",
        "com.moxi.minio.restApi",
        "com.moxi.minio.service",
        "com.moxi.minio.util",
        "com.moxi.minio.entity",
        "com.moxi.xo.utils",
        "com.moxi.xo.service",
        "com.moxi.commons.config",
        "com.moxi.utils",
})
@SpringBootApplication
public class MinioApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class,args);
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
