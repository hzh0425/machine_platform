package com.moxi.xo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.AuthUser;
import com.moxi.xo.vo.UserVO;
import org.apache.catalina.User;

import javax.security.auth.AuthPermission;
import java.util.List;


/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author hzh
 * @since 2020-09-01
 */
public interface AuthUserService extends SuperService<AuthUser> {

    /**
     * 获取用户列表
     * @param userVO
     * @return
     */
    public Page<AuthUser> getList(UserVO userVO);

    /**
     * 编辑用户
     */
    public String edit(UserVO userVO);

    /**
     * 新增用户
     */
    public String add(UserVO userVO);

    /**
     * 删除用户
     */
    public String delete(UserVO userVO);


    /**
     * 获取用户所对应的权限组uid
     * @param userUid
     * @return
     */
    public String getUserGroupId(String userUid);


}