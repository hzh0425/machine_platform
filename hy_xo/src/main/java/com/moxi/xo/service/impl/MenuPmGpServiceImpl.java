package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.enums.EStatus;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.MenuPmGp;
import com.moxi.commons.entity.MenuPmGpFK;
import com.moxi.commons.entity.UserPmGpFK;
import com.moxi.utils.RedisUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.StringUtils;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.RedisConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.MenuPmGpFKMapper;
import com.moxi.xo.mapper.MenuPmGpMapper;
import com.moxi.xo.mapper.UserPmGpFKMapper;
import com.moxi.xo.service.MenuPmGpFKService;
import com.moxi.xo.service.MenuPmGpService;
import com.moxi.xo.service.UserPmGpFKService;
import com.moxi.xo.vo.MenuPmGpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 14:51
 */
@Service
public class MenuPmGpServiceImpl extends SuperServiceImpl<MenuPmGpMapper, MenuPmGp> implements MenuPmGpService
{
    @Autowired
    MenuPmGpService permissionGroupService;

    @Autowired
    MenuPmGpFKService pmGpFKService;

    @Resource
    UserPmGpFKService userPmGpFKService;
    @Resource
    MenuPmGpFKMapper pmGpFKMapper;
    @Resource
    RedisUtil redisUtil;


    public IPage<MenuPmGp> getList(MenuPmGpVO permissionGroupVO){
        Page<MenuPmGp> page=new Page<>(permissionGroupVO.getCurrentPage(),permissionGroupVO.getPageSize());
        QueryWrapper<MenuPmGp> wrapper=new QueryWrapper<>();
//        List<MenuPermissionGroup> list= permissionGroupService.list(wrapper);
        IPage<MenuPmGp> list=permissionGroupService.page(page,wrapper);
        return list;
    }

    @Transactional
    public String add(MenuPmGpVO groupVO){
        String name=groupVO.getName();
        QueryWrapper<MenuPmGp> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.NAME,groupVO.getName());
        MenuPmGp group=permissionGroupService.getOne(wrapper);
        if(group==null){
            MenuPmGp gp=new MenuPmGp();
            gp.setName(groupVO.getName());
            gp.setSummary(groupVO.getSummary());
            gp.insert();
            if(StringUtils.isNotEmpty(groupVO.getMpids())){
                //插入记录到中间表
                pmGpFKService.addGroupPermission(gp.getUid(),groupVO.getMpids());
            }
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_EXIST);
        }
    }


    @Transactional
    public String edit(MenuPmGpVO groupVO){
        //查询是否存在
        MenuPmGp group=permissionGroupService.getById(groupVO.getUid());
        if(group==null){
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }
        group.setSummary(groupVO.getSummary());
        group.setName(groupVO.getName());
        group.updateById();
        if(StringUtils.isNotEmpty(groupVO.getMpids())&&groupVO.getChangePermission()){
            //如果该表了权限表
            //需要更新权限中间表
            //先删除,后插入
            updateMenuPermissionGroupFK(groupVO.getUid(),groupVO.getMpids());
        }
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.UPDATE_SUCCESS);
    }


    @Transactional
    public String delete(MenuPmGpVO groupVO){
        MenuPmGp  group=permissionGroupService.getById(groupVO.getUid());
        if(group!=null){
            //查询该权限组下是否有用户
            QueryWrapper<UserPmGpFK> wrapper=new QueryWrapper<>();
            wrapper.eq(SQLConf.MPID,group.getUid());
            int count=userPmGpFKService.count(wrapper);
            if(count>0){
                return ResultUtil.result(SysConf.ERROR,MessageConf.USER_UNDER_THIS_GROUP);
            }
            group.deleteById();
            //删除中间表中的记录
            pmGpFKService.deleteGroupPermission(groupVO.getUid());
            //删除redisu权限缓存
            DeleteRedisPermissionGroup(groupVO.getUid());
             return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }
    }

    public List<String> getPmIds(MenuPmGpVO menuPmGpVO){
        String uid=menuPmGpVO.getUid();
        return pmGpFKMapper.getGroupPmIds(uid);
    }

    @Async
    public void updateMenuPermissionGroupFK(String Gid,String mpids){
        //删除用户组原有权限
        pmGpFKService.deleteGroupPermission(Gid);
        //增加新权限
        pmGpFKService.addGroupPermission(Gid,mpids);
        //删除redis中权限组权限缓存集合
        DeleteRedisPermissionGroup(Gid);
    }

    public void DeleteRedisPermissionGroup(String Gid){
        String key= RedisConf.PM_GROUP_MENU + SysConf.REDIS_SEGMENTATION + Gid;
        redisUtil.delete(key);
    }
}
