package com.moxi.xo.service;

import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.AuthPmGpFk;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:43
 */
public interface AuthPmGpFkService extends SuperService<AuthPmGpFk> {
    public String saveBatch(List<String> pids, String pgid, String type) ;
}
