package com.moxi.admin.restapi;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.base.exception.ThrowableUtils;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.validator.group.Insert;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.MenuPmService;
import com.moxi.xo.vo.MenuPmVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/5 16:56
 */
@RestController
@RequestMapping("/button")
@Api(value = "按钮相关接口", tags = {"按钮相关接口"})
@Slf4j
public class buttonRestApi {
    @Autowired
    MenuPmService menuPmService;

    @AuthorityVerify
    @ApiOperation(value = "获取所有二级菜单-按钮列表", notes = "获取所有二级菜单-按钮列表", response = String.class)
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public String getList(MenuPmVO menuPmVO) {
        return ResultUtil.result(SysConf.SUCCESS, menuPmService.getButtonAllList(menuPmVO));
    }

    //    @AvoidRepeatableCommit
    @AuthorityVerify
    @ApiOperation(value = "增加菜单", notes = "增加菜单", response = String.class)
    @PostMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody MenuPmVO categoryMenuVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        return menuPmService.addCategoryMenu(categoryMenuVO);
    }

    @AuthorityVerify
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单", response = String.class)
    @PostMapping("/edit")
    public String edit(@Validated({Update.class}) @RequestBody MenuPmVO categoryMenuVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        return menuPmService.editCategoryMenu(categoryMenuVO);
    }

    @AuthorityVerify
    @ApiOperation(value = "删除菜单", notes = "删除菜单", response = String.class)
    @PostMapping("/delete")
    public String delete(@Validated({Delete.class}) @RequestBody MenuPmVO categoryMenuVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        return menuPmService.deleteCategoryMenu(categoryMenuVO);
    }

    //    @AuthorityVerify
    @ApiOperation(value = "置顶菜单", notes = "置顶菜单", response = String.class)
    @PostMapping("/stick")
    public String stick(@Validated({Delete.class}) @RequestBody MenuPmVO categoryMenuVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        return menuPmService.stickCategoryMenu(categoryMenuVO);
    }
}
