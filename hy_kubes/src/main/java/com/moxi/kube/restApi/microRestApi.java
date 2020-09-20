package com.moxi.kube.restApi;


import com.moxi.kube.server.KubeService;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.vo.DeploymentServiceVO;
import com.moxi.xo.vo.PortTypeVO;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/16 20:59
 */
@RestController
@RequestMapping("/microservice")
@Api(value = "微服务相关接口", tags = {"微服务相关接口"})
public class microRestApi {
    @Autowired
    KubeService kubeService;

//    @AuthorityVerify
//    @OperationLogger(value = "")
    @ApiOperation(value = "微服务部署", notes = "微服务部署", response = String.class)
    @PostMapping("/add")
    public String  CreateDeployment(@RequestBody DeploymentServiceVO service) {
        String uid= UUID.randomUUID().toString();
        service.deployment_id=uid;
        String owner="3bf3b8298cbe91d5246c511bc6659d99";
        service.owner=owner;
        return  kubeService.DeployMicroService(service);
    }

//    @AuthorityVerify
//    @OperationLogger(value = "")
    @ApiOperation(value = "删除微服务", notes = "删除微服务", response = String.class)
    @DeleteMapping("/delete/{deployment_id}")
    public String RemoveDeployment(@PathVariable String deployment_id) throws ApiException {
        if(deployment_id.equals("")){
            return ResultUtil.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        String owner="3bf3b8298cbe91d5246c511bc6659d99";
        return kubeService.deleteMicroService(deployment_id,owner);
    }

//    @AuthorityVerify
//    @OperationLogger(value = "")
    @ApiOperation(value = "获取自己部署的微服务", notes = "获取自己部署的微服务", response = String.class)
    @GetMapping("/getList")
    public String GetMyDeployments(@RequestBody DeploymentServiceVO service) {
        return ResultUtil.result(SysConf.SUCCESS,kubeService.getList(service));
    }

}
