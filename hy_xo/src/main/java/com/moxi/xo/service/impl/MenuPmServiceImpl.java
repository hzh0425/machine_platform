package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.enums.EStatus;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.MenuPm;
import com.moxi.utils.RedisUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.StringUtils;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.RedisConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.MenuPmMapper;
import com.moxi.xo.service.MenuPmGpFKService;
import com.moxi.xo.service.MenuPmService;
import com.moxi.xo.vo.MenuPmVO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:13
 */
@Service
public class MenuPmServiceImpl extends SuperServiceImpl<MenuPmMapper, MenuPm> implements MenuPmService {


    @Resource
    MenuPmMapper menuPmMapper;

    @Resource
    MenuPmService menuPmService;

    @Resource
    MenuPmGpFKService menuPmGpFKService;

    @Resource
    RedisUtil redisUtil;

    public List<MenuPm> getAllList(){

        return menuPmMapper.getAllList();
    }

    public List<MenuPm> getMenuPermissionList(String uid)
    {
        return menuPmMapper.getMenuPermissionList(uid);
    }

    @Override
    public  Page<MenuPm>  getPageList(MenuPmVO categoryMenuVO) {
        Page<MenuPm> page=new Page<>(categoryMenuVO.getCurrentPage(),categoryMenuVO.getPageSize());
        return menuPmMapper.getAllList(page);
    }

    @Override
    public Page<MenuPm> getButtonAllList(MenuPmVO menuPmVO) {
        Page<MenuPm> page=new Page<>(menuPmVO.getCurrentPage(),menuPmVO.getPageSize());
        return menuPmMapper.getAllChildMenu(page);
    }

    @Override
    public String addCategoryMenu(MenuPmVO menuPmVO) {
        //如果是一级菜单，将父ID清空
        if (menuPmVO.getMenuLevel() == 1) {
            menuPmVO.setParentUid("");
        }
        MenuPm categoryMenu = new MenuPm();
        categoryMenu.setParentUid(menuPmVO.getParentUid());
        categoryMenu.setSort(menuPmVO.getSort());
        categoryMenu.setIcon(menuPmVO.getIcon());
        categoryMenu.setMenuLevel(menuPmVO.getMenuLevel());
        categoryMenu.setMenuType(menuPmVO.getMenuType());
        categoryMenu.setName(menuPmVO.getName());
        categoryMenu.setUrl(menuPmVO.getUrl());
        categoryMenu.setUpdateTime(new Date());
        categoryMenu.insert();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String editCategoryMenu(MenuPmVO categoryMenuVO) {
        MenuPm categoryMenu = menuPmService.getById(categoryMenuVO.getUid());
        if(categoryMenu==null){
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }
        categoryMenu.setParentUid(categoryMenuVO.getParentUid());
        categoryMenu.setSort(categoryMenuVO.getSort());
        categoryMenu.setIcon(categoryMenuVO.getIcon());
        categoryMenu.setMenuLevel(categoryMenuVO.getMenuLevel());
        categoryMenu.setMenuType(categoryMenuVO.getMenuType());
        categoryMenu.setName(categoryMenuVO.getName());
        categoryMenu.setUrl(categoryMenuVO.getUrl());
        categoryMenu.setUpdateTime(new Date());
        categoryMenu.updateById();



        return ResultUtil.result(SysConf.SUCCESS, MessageConf.UPDATE_SUCCESS);
    }

    @Override
    @Transactional
    public String deleteCategoryMenu(MenuPmVO categoryMenuVO) {
        MenuPm menuPm=menuPmService.getById(categoryMenuVO.getUid());
        if(menuPm==null){
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }
        /**
         * 查询该实体下是否有子菜单
         */
        QueryWrapper<MenuPm> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.PARENT_UID,categoryMenuVO.getUid());
        int count=menuPmService.count(wrapper);
        if(count>0){
            return ResultUtil.result(SysConf.ERROR,MessageConf.CHILDREN_MENU_UNDER_THIS_MENU);
        }

        menuPm.deleteById();

        /**
         * 删除权限组关联数据
         */
        menuPmGpFKService.deleteByPermissionId(categoryMenuVO.getUid());


        return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
    }

    @Override
    public String stickCategoryMenu(MenuPmVO categoryMenuVO) {
        MenuPm menuPm=menuPmService.getById(categoryMenuVO.getUid());
        if(menuPm==null){
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }
        QueryWrapper<MenuPm> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.MENU_LEVEL,categoryMenuVO.getMenuLevel());
        if(StringUtils.isNotEmpty(categoryMenuVO.getParentUid())){
            //找出同一父类下的所有菜单
            wrapper.eq(SQLConf.PARENT_UID,categoryMenuVO.getParentUid());
        }
        wrapper.select(SQLConf.SORT);
        wrapper.orderByDesc(SQLConf.SORT);
        wrapper.last(SQLConf.LAST);
        MenuPm maxSort=menuPmService.getOne(wrapper);
        menuPm.setSort(maxSort.getSort()+1);
        menuPm.setUpdateTime(new Date());
        menuPm.updateById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.UPDATE_SUCCESS);
    }


}
