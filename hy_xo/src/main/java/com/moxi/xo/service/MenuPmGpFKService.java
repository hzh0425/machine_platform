package com.moxi.xo.service;

import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.MenuPmGpFK;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:19
 */
public interface MenuPmGpFKService extends SuperService<MenuPmGpFK> {
    /**
     * 增加权限组权限
     */
    public Boolean addGroupPermission(String Gid,String pids);
    /**
     * 删除权限
     */
    public Boolean deleteGroupPermission(String Gid);

    /**
     * 根据permissionId删除中间表记录
     * @param pId
     * @return
     */
    public Boolean deleteByPermissionId(String pId);
}
