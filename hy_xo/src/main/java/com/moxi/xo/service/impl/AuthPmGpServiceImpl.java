package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.*;
import com.moxi.utils.RedisUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.StringUtils;
import com.moxi.xo.global.MessageConf;

import com.moxi.xo.global.RedisConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.AuthPmGpMapper;
import com.moxi.xo.service.AuthPmGpService;
import com.moxi.xo.service.AuthUserPmGpFkService;
import com.moxi.xo.service.MicroDeploymentService;
import com.moxi.xo.service.trainSetService;
import com.moxi.xo.vo.AuthPmGpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:48
 */
@Service
public class AuthPmGpServiceImpl extends SuperServiceImpl<AuthPmGpMapper, AuthPmGp> implements AuthPmGpService {
    @Resource
    AuthPmGpService service;
    @Resource
    AuthUserPmGpFkService userFkService;
    @Resource
    AuthPmGpMapper mapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MicroDeploymentService deploymentService;
    @Autowired
    trainSetService setService;
    @Override
    public IPage<AuthPmGp> getList(AuthPmGpVO vo) {
        QueryWrapper<AuthPmGp>wrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(vo.getName())){
            wrapper.like(SQLConf.NAME,vo.getName());
        }
        wrapper.orderByDesc(SQLConf.CRAETE_DATE);
        Page<AuthPmGp> page=new Page<>(vo.getCurrentPage(),vo.getPageSize());
        return service.page(page,wrapper);
    }

    @Override
    public String add(AuthPmGpVO vo) {
        QueryWrapper<AuthPmGp> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.NAME,vo.getName());
        AuthPmGp pre=service.getOne(wrapper);
        if(pre!=null)return ResultUtil.result(SysConf.ERROR, MessageConf.ENTITY_EXIST);
        AuthPmGp entity=new AuthPmGp();
        entity.setCreate_date(new Date());
        entity.setDescription(vo.getDescription());
        entity.setName(vo.getName());
        entity.insert();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String edit(AuthPmGpVO vo) {
        AuthPmGp pre=service.getById(vo.getPgid());
        if(pre==null)return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        pre.setName(vo.getName());
        pre.setDescription(vo.getDescription());
        pre.updateById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String delete(AuthPmGpVO vo) {
        AuthPmGp pre=service.getById(vo.getPgid());
        if(pre==null)return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        //查询改组下是否有相关用户
        QueryWrapper<AuthUserPmGpFk> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.PGID,vo.getPgid());
        int count=userFkService.count(wrapper);
        if(count>0)return ResultUtil.result(SysConf.ERROR,MessageConf.USER_UNDER_THIS_GROUP);
        //删除
        pre.deleteById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String getPermissions(AuthPmGpVO vo) {
        //1.先查询redis下是否有该权限组该类型的权限的id集合
        String resourceIdKey= RedisConf.SET_PERMISSION_ID+vo.getPgid()+RedisConf.RESOURCE_TYPE+vo.getPermissionType();;
        List<String> resourceIdList;
        if(redisUtil.hasKey(resourceIdKey)){
            System.out.println("get from redis");
            Set<String> list = redisUtil.sMembers(resourceIdKey);
            resourceIdList= new ArrayList<>();
            for (String item:list){
                System.out.println(item);
                resourceIdList.add(item);
            }
        }else{
            System.out.println("get from mysql");
            //查询mysql,缓存到redis,同时缓存该权限类型的url集合
            List<AuthPm> items=mapper.getGroupPermissionAll(vo.getPgid());
            //异步保存到redis,总共保存了所有的url,各个类别的权限ids
            savePermissionToRedisAsync(items,vo.getPgid());
            //抽出所需type id
            resourceIdList=items.stream().filter(item->item.getResourceType()==vo.getPermissionType()).map(x->x.getResourceId()).collect(Collectors.toList());
        }

        //2.根据resourceId查询相关类型的权限
        if(resourceIdList.size()<=0)return ResultUtil.result(SysConf.SUCCESS,new ArrayList<>());
        switch (vo.getPermissionType()){
            case SQLConf.RESOURCE_TYPE_DEPLOYMENT:{
                //模型的资源,后面要改成openfeign调用服务
                QueryWrapper<MicroDeployment> wrapper=new QueryWrapper<>();
                wrapper.in(SQLConf.DEPLOYMENT_ID,resourceIdList);
                return ResultUtil.result(SysConf.SUCCESS,deploymentService.list(wrapper));
            }
            case SQLConf.RESOURCE_TYPE_PROXY:{
                //代理资源
                //QueryWrapper<>wrapper=new QueryWrapper();

            }
            case SQLConf.RESOURCE_TYPE_TRAINSET:{
                //训练集资源
                QueryWrapper<trainSet> wrapper=new QueryWrapper<>();
                wrapper.in(SQLConf.UID,resourceIdList);
                return ResultUtil.result(SysConf.SUCCESS,setService.list(wrapper));
            }
        }
        return ResultUtil.result(SysConf.ERROR,MessageConf.PARAM_INCORRECT);
    }

    public void savePermissionToRedisAsync(List<AuthPm> list,String pgid){
        //缓存url List,用于后期权限判定
        String urlKey=RedisConf.SET_PERMISSION_URL+pgid;
        List<String> urlList=list.stream().map(x->x.getUrl()).collect(Collectors.toList());
        redisUtil.sAdd(urlKey,urlList.stream().toArray(String[]::new));
        redisUtil.expire(urlKey,5, TimeUnit.DAYS);
        //缓存各种类型的id Type
        savePermissionToRedisWithType(list, SQLConf.RESOURCE_TYPE_DEPLOYMENT,pgid);
        savePermissionToRedisWithType(list,SQLConf.RESOURCE_TYPE_PROXY,pgid);
        savePermissionToRedisWithType(list,SQLConf.RESOURCE_TYPE_TRAINSET,pgid);
    }
    public void savePermissionToRedisWithType(List<AuthPm> list,int type,String pgid){
        String key=RedisConf.SET_PERMISSION_ID+pgid+RedisConf.RESOURCE_TYPE+type;;
        List<String>ids=list.stream().filter(x->x.getResourceType()==type).map(x->x.getResourceId()).collect(Collectors.toList());
        if(ids.size()>0){
            redisUtil.sAdd(key,ids.stream().toArray(String[]::new));
            redisUtil.expire(key,5,TimeUnit.DAYS);
        }
    }

}
