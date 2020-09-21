package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 0:01
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "main.controller",
        "main.server"
})
public class webApplication {
    public static void main(String[] args) {
        SpringApplication.run(webApplication.class,args);
    }
}

