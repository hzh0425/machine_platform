package com.moxi.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.trainSet;
import com.moxi.xo.vo.trainSetVO;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 20:39
 */
public interface trainSetService extends SuperService<trainSet> {
    public String add(trainSetVO setVO);
    public String delete(trainSetVO setVO);
    public IPage<trainSet> getList(trainSetVO setVO);
}
