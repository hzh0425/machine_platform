package com.moxi.admin.restapi;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.base.exception.ThrowableUtils;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.validator.group.Insert;
import com.moxi.base.validator.group.Update;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.AuthUserService;
import com.moxi.xo.vo.UserVO;
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
 * @date 2020/9/4 15:37
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理相关接口", tags = {"用户管理相关接口"})
@Slf4j
public class userRestApi {

    @Autowired
    AuthUserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表", response = String.class)
    @GetMapping("/getList")
    public String getList(@Validated({GetList.class}) @RequestBody UserVO userVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return ResultUtil.result(SysConf.SUCCESS,userService.getList(userVO));
    }

    @ApiOperation(value = "编辑用户", notes = "编辑用户", response = String.class)
    @GetMapping("/edit")
    public String edit(@Validated({Update.class}) @RequestBody UserVO userVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return userService.edit(userVO);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户", response = String.class)
    @GetMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody UserVO userVO, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return userService.add(userVO);
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", response = String.class)
    @GetMapping("/delete")
    public String delete(@RequestBody UserVO userVO) {
        return userService.delete(userVO);
    }
}
