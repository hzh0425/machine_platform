package com.moxi.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/1 22:24
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.moxi.commons.config",
        "com.moxi.utils",
        "com.moxi.admin.annotion",
        "com.moxi.admin.security",
        "com.moxi.admin.config",
        "com.moxi.admin.restapi",
        "com.moxi.xo.utils",
        "com.moxi.xo.service"
}) 
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
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
