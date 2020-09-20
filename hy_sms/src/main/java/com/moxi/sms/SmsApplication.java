package com.moxi.sms;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
//@EnableEurekaServer
@EnableDiscoveryClient
@EnableRabbit
@EnableFeignClients("com.moxi.sms.feign")
@ComponentScan(basePackages = {
        "com.moxi.utils",
        "com.moxi.sms.feign",
        "com.moxi.sms.listener",
        "com.moxi.sms.config",
        "com.moxi.sms.util",
})
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
