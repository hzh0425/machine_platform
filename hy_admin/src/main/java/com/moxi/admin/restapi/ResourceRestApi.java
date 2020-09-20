package com.moxi.admin.restapi;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 21:37
 */
@RestController
@RequestMapping("/resource")
@Api(value = "资源相关接口", tags = {"资源相关接口"})
@Slf4j
public class ResourceRestApi {


}

