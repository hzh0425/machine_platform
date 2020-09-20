package com.moxi.grpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.moxi.grpc.restApi"))
                //这个是所在的controller,要自己修改
                .paths(PathSelectors.any())

                .build();

    }

    //配置在线文档的基本信息

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()

                .title("springboot-swagger")

                .description("grpc相关接口")

                .termsOfServiceUrl("https://me.csdn.net/blog/miachen520")

                .version("3.5")

                .build();

    }





}

