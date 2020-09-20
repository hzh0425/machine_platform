package com.moxi.xo.service.impl;

import com.moxi.base.serviceImpl.SuperServiceImpl;
import com.moxi.commons.entity.MicroDeployment;
import com.moxi.xo.mapper.MicroDeploymentMapper;
import com.moxi.xo.service.MicroDeploymentService;
import org.springframework.stereotype.Service;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 0:37
 */
@Service
public class MicroDeploymentServiceImpl  extends SuperServiceImpl<MicroDeploymentMapper, MicroDeployment> implements MicroDeploymentService {
}
