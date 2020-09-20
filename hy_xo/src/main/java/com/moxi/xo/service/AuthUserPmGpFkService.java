package com.moxi.xo.service;

import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.AuthUserPmGpFk;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:43
 */
public interface AuthUserPmGpFkService extends SuperService<AuthUserPmGpFk> {
    public String saveBatch(List<String> userIds, String pgid, String add);
}
