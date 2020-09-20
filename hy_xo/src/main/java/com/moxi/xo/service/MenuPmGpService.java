package com.moxi.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.MenuPmGp;
import com.moxi.xo.vo.MenuPmGpVO;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 14:50
 */
public interface MenuPmGpService extends SuperService<MenuPmGp> {
    /**
     * 获取权限组列表
     */
    public IPage<MenuPmGp> getList(MenuPmGpVO permissionGroupVO);
    /**
     * 新增权限组
     */
    public String add(MenuPmGpVO groupVO);
    /**
     * 更新权限组
     */
    public String edit(MenuPmGpVO groupVO);
    /**
     * 删除权限组
     */
    public String delete(MenuPmGpVO groupVO);

    /**
     * 获取权限组的权限的ids
     * @param menuPmGpVO
     * @return
     */
    public List<String> getPmIds(MenuPmGpVO menuPmGpVO);
}
