package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.AuthUser;
import com.moxi.commons.entity.UserPmGpFK;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.AuthUserMapper;
import com.moxi.xo.mapper.UserPmGpFKMapper;
import com.moxi.xo.service.AuthUserService;
import com.moxi.xo.service.UserPmGpFKService;
import com.moxi.xo.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.AuthPermission;
import java.util.List;


/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author hzh
 * @since 2020-09-01
 */
@Service
public class AuthUserServiceImpl extends SuperServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {
    @Resource
    AuthUserMapper authUserMapper;

    @Resource
    AuthUserService userService;

    @Resource
    UserPmGpFKMapper userPmGpFKMapper;

    @Override
    public Page<AuthUser> getList(UserVO userVO) {
        Page<AuthUser> page=new Page<>(userVO.getCurrentPage(),userVO.getPageSize());
        return authUserMapper.getList(page);
    }

    @Override
    public String edit(UserVO userVO) {
        AuthUser user=userService.getById(userVO.getUid());
        if(user==null){
            return ResultUtil.result(SysConf.ERROR, MessageConf.ENTITY_NOT_EXIST);
        }
        user.setNickname(userVO.getNickName());
        user.setPhone(userVO.getPhone());
        user.setEmail(userVO.getEmail());
        user.updateById();
        /**
         * 更新权限中间表
         */
        UserPmGpFK fk=new UserPmGpFK();
        fk.setUid(user.getUid());
        fk.setMpid(userVO.getMenuGroupUid());
        fk.updateById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.UPDATE_SUCCESS);
    }

    @Override
    public String add(UserVO userVO) {
        return null;
    }

    @Override
    public String delete(UserVO userVO) {
        return null;
    }

    public String getUserGroupId(String userUid){
        return userPmGpFKMapper.getUserGroupUid(userUid);
    }
}
