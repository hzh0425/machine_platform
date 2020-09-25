package com.moxi.kube;

import com.moxi.kube.server.KubeService;
import com.moxi.kube.util.KubeUtil;
import io.kubernetes.client.openapi.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/15 21:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    KubeUtil cubeUtil;

    @Test
    public void test1() throws ApiException {
        cubeUtil.getPodStatus("creatorcloud");
    }
}
