package com.moxi.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.MenuPmGpFK;
import com.moxi.utils.StringUtils;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.mapper.MenuPmGpFKMapper;
import com.moxi.xo.service.MenuPmGpFKService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/4 14:49
 */
@Service
public class MenuPmGpFKServiceImpl extends SuperServiceImpl<MenuPmGpFKMapper, MenuPmGpFK> implements MenuPmGpFKService {

    @Resource
    MenuPmGpFKService pmGpFKService;


    /**
     * require事务
     * @param Gid
     * @param pids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Async
    public Boolean addGroupPermission(String Gid,String pids){
        if(StringUtils.isEmpty(pids)||StringUtils.isEmpty(Gid)){
            return false;
        }
        String[] ids = pids.split(SysConf.FILE_SEGMENTATION);
        List<MenuPmGpFK> list=new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            MenuPmGpFK fk=new MenuPmGpFK();
            fk.setMpid(ids[i]);
            fk.setMpgid(Gid);
            list.add(fk);
        }
        System.out.println(list);
        return pmGpFKService.saveBatch(list);

    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Async
    public Boolean deleteGroupPermission(String Gid){
        QueryWrapper<MenuPmGpFK> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.MPGID,Gid);
        return pmGpFKService.remove(wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Async
    public Boolean deleteByPermissionId(String pId){
        QueryWrapper<MenuPmGpFK> wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.MPID,pId);
        return pmGpFKService.remove(wrapper);
    }
}
