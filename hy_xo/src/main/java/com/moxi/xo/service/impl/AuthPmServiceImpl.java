package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthUser;
import com.moxi.xo.mapper.AuthPmMapper;
import com.moxi.xo.mapper.AuthUserMapper;
import com.moxi.xo.service.AuthPmService;
import com.moxi.xo.service.AuthUserService;
import com.moxi.xo.vo.AuthPmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:44
 */
@Service
public class AuthPmServiceImpl  extends SuperServiceImpl<AuthPmMapper, AuthPm> implements AuthPmService {
    @Autowired
    AuthPmService service;
    @Autowired
    AuthPmMapper mapper;
    @Override
    public IPage<AuthPm> getList(AuthPmVO vo) {
        return null;
    }

    @Override
    public String add(AuthPmVO vo) {
        return null;
    }

    @Override
    public String edit(AuthPmVO vo) {
        return null;
    }

    @Override
    public String delete(AuthPmVO vo) {
        return null;
    }
}
