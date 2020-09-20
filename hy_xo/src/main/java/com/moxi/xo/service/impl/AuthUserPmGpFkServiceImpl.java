package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthUserPmGpFk;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.AuthPmMapper;
import com.moxi.xo.mapper.AuthUserMapper;
import com.moxi.xo.mapper.AuthUserPmGpFkMapper;
import com.moxi.xo.service.AuthPmService;
import com.moxi.xo.service.AuthUserPmGpFkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:49
 */
@Service
public class AuthUserPmGpFkServiceImpl extends SuperServiceImpl<AuthUserPmGpFkMapper, AuthUserPmGpFk> implements AuthUserPmGpFkService {
    @Resource
    AuthUserPmGpFkService service;
    @Override
    public String saveBatch(List<String> userIds, String pgid, String type) {
        if(type.equals(SysConf.ADD)){
            List<AuthUserPmGpFk> list=new ArrayList<>();
            userIds.forEach(item->{
                list.add(new AuthUserPmGpFk(item,pgid));
            });
            service.saveBatch(list);
            return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
        }else if(type.equals(SysConf.DELETE)){
            QueryWrapper<AuthUserPmGpFk> wrapper=new QueryWrapper<>();
            wrapper.eq(SQLConf.PGID,pgid);
            wrapper.in(SQLConf.UID,userIds);
            service.remove(wrapper);
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
        }
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.OPERATION_SUCCESS);
    }
}
