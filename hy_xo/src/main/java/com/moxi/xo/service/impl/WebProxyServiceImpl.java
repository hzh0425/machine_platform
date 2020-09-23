package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthPmGp;
import com.moxi.commons.entity.MicroDeployment;
import com.moxi.commons.entity.WebProxy;
import com.moxi.commons.feign.envoyFeign;
import com.moxi.utils.ResultUtil;

import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.AuthPmMapper;
import com.moxi.xo.mapper.WebProxyMapper;
import com.moxi.xo.service.AuthPmService;
import com.moxi.xo.service.MicroDeploymentService;
import com.moxi.xo.service.WebProxyService;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 21:30
 */
@Service
public class WebProxyServiceImpl extends SuperServiceImpl<WebProxyMapper, WebProxy> implements WebProxyService {
    @Autowired
    com.moxi.commons.feign.envoyFeign envoyFeign;
    @Autowired
    WebProxyService proxyService;
    @Autowired
    MicroDeploymentService deploymentService;
    public String delete(String proxy_id) {
        WebProxy pre=proxyService.getById(proxy_id);
        if(pre==null)return ResultUtil.result(SysConf.ERROR, MessageConf.ENTITY_NOT_EXIST);
        if(envoyFeign.delete(proxy_id)){
            pre.deleteById();
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
        }else return ResultUtil.result(SysConf.ERROR,MessageConf.DELETE_FAIL);
    }

    public String add(String deployment_id, int port) {
        //先根据deploymentId查询相关微服务
        QueryWrapper<MicroDeployment> deployWrapper=new QueryWrapper<>();
        deployWrapper.eq(SQLConf.DEPLOYMENT_ID,deployment_id);
        MicroDeployment deploy = deploymentService.getById(deployment_id);
        if(deploy==null)return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        //查询是否已经设置了相关代理
        QueryWrapper<WebProxy> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.CLUSTER_IP,deploy.getClusterIp());
        WebProxy proxy=proxyService.getOne(wrapper);
        if(proxy!=null)return ResultUtil.result(SysConf.ERROR,MessageConf.ALREADY_PROXY);
        //创建代理
        System.out.println(deploy);
        WebProxy item=new WebProxy();
        item.setProxyId(UUID.randomUUID().toString());
        item.setCluster_ip(deploy.getClusterIp());
        item.setDeployment_id(deploy.getDeploymentId());
        item.setPort(port);
        //调用openfeign,改变XDS服务发现
        if(envoyFeign.add(item.getProxyId(),deploy.getClusterIp(),port)){
            item.insert();
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.INSERT_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR,MessageConf.INSERT_FAIL);
        }
    }

    public List<WebProxy> getList() {
        return proxyService.list();
    }
}
