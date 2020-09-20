package com.moxi.socket;

import com.moxi.socket.handler.serverHandler;
import com.moxi.socket.start.serverStart;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetSocketAddress;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/11 14:54
 */
@SpringBootApplication
public class SocketApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SocketApplication.class,args);
        serverStart nettyStart=new serverStart();
        nettyStart.start(new InetSocketAddress("127.0.0.1",8888));
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
