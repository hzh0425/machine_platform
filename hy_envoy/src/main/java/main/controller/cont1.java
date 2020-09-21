package main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 0:02
 */
@RestController
public class cont1 {
    @GetMapping("/envoy-server/hello")
    public String hello() {
        System.out.println("get request from remote, send response, say hello");
        return "hello";
    }

    @GetMapping("/envoy-server/hi")
    public String hi() {
        System.out.println("get request from remote, send response, say hi");
        return "hi";
    }

    @GetMapping("/envoy-server/self")
    public String self() {
        System.out.println("get request from remote, send response, say self");
        return "self";
    }

}
