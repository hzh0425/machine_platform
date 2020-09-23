package main;


import main.server.XdsServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 20:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test1 {
    @Autowired
    XdsServer server;
    
}
