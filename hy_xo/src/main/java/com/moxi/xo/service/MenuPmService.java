package com.moxi.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.MenuPm;
import com.moxi.xo.vo.MenuPmVO;

import java.util.List;
import java.util.Map;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:12
 */
public interface MenuPmService extends SuperService<MenuPm> {
    /**
     * 获取所有的权限列表
     */
    public List<MenuPm> getAllList();
    /**
     * 获取能访问的权限列表
     * @param uid
     * @return
     */
    public List<MenuPm> getMenuPermissionList(String uid);


    /**
     * 获取菜单列表
     *
     * @param categoryMenuVO
     * @return
     */
    public  Page<MenuPm>  getPageList(MenuPmVO categoryMenuVO);


    /**
     * 获取所有二级菜单-按钮列表
     *
     * @param
     * @return
     */
    public Page<MenuPm> getButtonAllList(MenuPmVO menuPmVO);

    /**
     * 新增菜单
     *
     * @param menuPmVO
     */
    public String addCategoryMenu(MenuPmVO menuPmVO);

    /**
     * 编辑菜单
     *
     * @param categoryMenuVO
     */
    public String editCategoryMenu(MenuPmVO categoryMenuVO);

    /**
     * 批量删除菜单
     *
     * @param categoryMenuVO
     */
    public String deleteCategoryMenu(MenuPmVO categoryMenuVO);

    /**
     * 置顶菜单
     *
     * @param categoryMenuVO
     */
    public String stickCategoryMenu(MenuPmVO categoryMenuVO);
}
