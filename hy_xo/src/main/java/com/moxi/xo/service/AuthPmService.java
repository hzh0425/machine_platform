package com.moxi.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.AuthPm;
import com.moxi.xo.vo.AuthPmGpVO;
import com.moxi.xo.vo.AuthPmVO;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:41
 */
public interface AuthPmService extends SuperService<AuthPm> {
    IPage<AuthPm> getList(AuthPmVO vo);

    String add(AuthPmVO vo);

    String edit(AuthPmVO vo);

    String delete(AuthPmVO vo);
}
