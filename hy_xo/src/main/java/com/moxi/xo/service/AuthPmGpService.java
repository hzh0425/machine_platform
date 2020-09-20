package com.moxi.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthPmGp;
import com.moxi.xo.vo.AuthPmGpVO;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:42
 */
public interface AuthPmGpService extends SuperService<AuthPmGp> {
    IPage<AuthPmGp> getList(AuthPmGpVO vo);

    String add(AuthPmGpVO vo);

    String edit(AuthPmGpVO vo);

    String delete(AuthPmGpVO vo);


    String getPermissions(AuthPmGpVO vo);
}
