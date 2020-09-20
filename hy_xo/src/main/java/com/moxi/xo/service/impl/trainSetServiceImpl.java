package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.trainSet;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.trainSetMapper;
import com.moxi.xo.service.trainSetService;
import com.moxi.xo.vo.trainSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 20:39
 */
@Service
public class trainSetServiceImpl extends SuperServiceImpl<trainSetMapper, trainSet> implements trainSetService {

    @Autowired
    trainSetService setService;

    @Override
    public String add(trainSetVO setVO) {
        trainSet trainSet=new trainSet();
        trainSet.setUserUid(setVO.getUserUid());
        trainSet.setUrl(setVO.getUrl());
        trainSet.setFileSize(setVO.getFileSize());
        trainSet.setUid(setVO.getUid());
        trainSet.setChunkSize(setVO.getChunkSize());
        trainSet.setUid(setVO.getUid());
        trainSet.setFileName(setVO.getFileName());
        trainSet.setCreateTime(new Date());
        trainSet.insert();
        return ResultUtil.result(SysConf.SUCCESS, MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String delete(trainSetVO setVO) {
        QueryWrapper<trainSet> wrapper=new QueryWrapper<>();
        wrapper.eq(SysConf.UID,setVO.getUid());
        trainSet set=setService.getOne(wrapper);
        if(set!=null){
            set.deleteById();
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.INSERT_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        }

    }

    @Override
    public IPage<trainSet> getList(trainSetVO setVO) {
        Page<trainSet> page=new Page<>(setVO.getCurrentPage(),setVO.getPageSize());
        QueryWrapper<trainSet> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.USER_UID,setVO.getUserUid());
        return setService.page(page,wrapper);
    }
}
