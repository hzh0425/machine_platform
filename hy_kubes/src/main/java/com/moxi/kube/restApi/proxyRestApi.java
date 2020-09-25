package com.moxi.kube.restApi;


import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.WebProxyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 14:10
 */
@RestController
@RequestMapping("/proxy")
//@Api(value = "微服务代理相关接口", tags = {"微服务代理相关接口"})
public class proxyRestApi {
    @Autowired
    WebProxyService proxyService;
    @ApiOperation(value = "1.获取已经设置的代理列表", notes = "1.获取已经设置的代理列表")
    @GetMapping("/getList")
    public String getList()
    {
        return ResultUtil.result(SysConf.SUCCESS,proxyService.getList());
    }


    @ApiOperation(value = "2.新增代理", notes = "2.新增代理", response = String.class)
    @PostMapping("/add")
    public String add(@RequestParam("deployment_id")String deployment_id,@RequestParam("port")int port) {
        return proxyService.add(deployment_id,port);
    }

    @ApiOperation(value = "3.删除代理", notes = "3.删除代理", response = String.class)
    @PostMapping("/delete")
    public String delete(@RequestParam("proxy_id")String proxy_id){
        return proxyService.delete(proxy_id);
    }


}
