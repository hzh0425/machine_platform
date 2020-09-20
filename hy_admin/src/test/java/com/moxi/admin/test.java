package com.moxi.admin;

import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthPmGp;
import com.moxi.xo.mapper.AuthPmGpMapper;
import com.moxi.xo.service.AuthPmGpService;
import com.moxi.xo.vo.AuthPmGpVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/18 9:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Autowired
    AuthPmGpService service;
    @Test
    public void test1(){
        AuthPmGpVO group=new AuthPmGpVO();
        group.setPgid("b372fe64-739a-4397-9c88-2def43344c2a");
        group.setPermissionType(1);
        String permissions = service.getPermissions(group);
        System.out.println(permissions);
        AuthPmGpVO group1=new AuthPmGpVO();
        group1.setPgid("b372fe64-739a-4397-9c88-2def43344c2a");
        group1.setPermissionType(2);
        String permissions1 = service.getPermissions(group1);
        System.out.println(permissions1);
    }
}
