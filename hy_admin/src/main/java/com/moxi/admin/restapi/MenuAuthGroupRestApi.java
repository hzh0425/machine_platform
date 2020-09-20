package com.moxi.admin.restapi;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.base.exception.ThrowableUtils;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.validator.group.Insert;
import com.moxi.base.validator.group.Update;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.MenuPmGpService;
import com.moxi.xo.vo.MenuPmGpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 11:20
 */
@RestController
@RequestMapping("/MenuAuthGroup")
@Api(value = "页面权限组相关接口", tags = {"页面权限组相关接口"})
@Slf4j
public class MenuAuthGroupRestApi {
    @Autowired
    MenuPmGpService pmGpService;

    @AuthorityVerify
    @ApiOperation(value = "get", notes = "获取权限组列表", response = String.class)
    @PostMapping("/getList")
    public String getList(@Validated({GetList.class}) @RequestBody MenuPmGpVO permissionGroupVO, BindingResult result)
    {
        ThrowableUtils.checkParamArgument(result);
        return ResultUtil.result(SysConf.SUCCESS, pmGpService.getList(permissionGroupVO));
    }


    @ApiOperation(value = "获取权限组的权限permissionIds", notes = "获取权限组的权限permissionIds", response = String.class)
    @PostMapping("/getPmIds")
    public String getPmIds(@RequestBody MenuPmGpVO menuPmGpVO) {
        return ResultUtil.result(SysConf.SUCCESS, pmGpService.getPmIds(menuPmGpVO));
    }

    @AuthorityVerify
    @ApiOperation(value = "新增权限组", notes = "新增权限组", response = String.class)
    @PostMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody MenuPmGpVO permissionGroupVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return pmGpService.add(permissionGroupVO);
    }

    @AuthorityVerify
    @ApiOperation(value = "更新权限组信息", notes = "更新权限组信息", response = String.class)
    @PostMapping("/edit")
    public String edit(@Validated({Update.class}) @RequestBody MenuPmGpVO permissionGroupVO ) {
        System.out.println("---"+permissionGroupVO.getChangePermission());
        return pmGpService.edit(permissionGroupVO);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除权限组", notes = "删除权限组", response = String.class)
    @PostMapping("/delete")
    public String delete(@RequestBody MenuPmGpVO permissionGroupVO){
        return pmGpService.delete(permissionGroupVO);
    }




}
