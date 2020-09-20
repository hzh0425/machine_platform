package com.moxi.admin.restapi;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.admin.global.MessageConf;
import com.moxi.admin.global.SQLConf;
import com.moxi.base.exception.ThrowableUtils;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.validator.group.Insert;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.StringUtils;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.AuthPmGpFkService;
import com.moxi.xo.service.AuthPmGpService;
import com.moxi.xo.service.AuthPmService;
import com.moxi.xo.service.AuthUserPmGpFkService;
import com.moxi.xo.vo.AuthPmGpVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:59
 */
@RestController
@RequestMapping("/resourceGroup")
@Api(value = "资源权限组相关接口", tags = {"资源权限组相关接口"})
@Slf4j
public class ResourceGroupRestApi {
    @Autowired
    AuthPmGpService service;
    @Autowired
    AuthUserPmGpFkService userFkService;
    @Autowired
    AuthPmGpFkService pmFkService;

    /**
     * 权限组增删改查
     */



    @ApiOperation(value = "获取列表", notes = "获取列表", response = String.class)
    @PostMapping("/getList")
    public String getList(@Validated({GetList.class}) @RequestBody AuthPmGpVO vo, BindingResult result)
    {
        ThrowableUtils.checkParamArgument(result);
        return ResultUtil.result(SysConf.SUCCESS, service.getList(vo));
    }




    @ApiOperation(value = "新增", notes = "新增", response = String.class)
    @PostMapping("/add")
    public String add(@Validated({Insert.class}) @RequestBody AuthPmGpVO vo, BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return service.add(vo);
    }


    @ApiOperation(value = "更新信息", notes = "更新信息", response = String.class)
    @PostMapping("/edit")
    public String edit(@Validated({Update.class}) @RequestBody AuthPmGpVO vo,BindingResult result) {
        ThrowableUtils.checkParamArgument(result);
        return service.edit(vo);
    }


    @ApiOperation(value = "删除", notes = "删除", response = String.class)
    @PostMapping("/delete")
    public String delete(@Validated({Delete.class}) @RequestBody AuthPmGpVO vo,BindingResult result ){
        ThrowableUtils.checkParamArgument(result);
        return service.delete(vo);
    }

    /**
     * 权限组--权限 增删改
     * @param pIds
     * @param pgid
     * @return
     */

    @ApiOperation(value = "权限组批量添加权限", notes = "权限组批量添加权限", response = String.class)
    @PostMapping("/addPermissionBatch")
    public String addPermissionBatch(@RequestParam("pIds")List<String>pIds,@RequestParam("pgid")String pgid) {
        return ResultUtil.result(SysConf.SUCCESS,pmFkService.saveBatch(pIds,pgid, SQLConf.ADD));
    }
    @ApiOperation(value = "权限组批量删除权限", notes = "权限组批量删除权限", response = String.class)
    @PostMapping("/deletePermissionBatch")
    public String deletePermissionBatch(@RequestParam("pIds")List<String>pIds,@RequestParam("pgid")String pgid) {
        return ResultUtil.result(SysConf.SUCCESS,pmFkService.saveBatch(pIds,pgid, SQLConf.DELETE));
    }

    @ApiOperation(value = "获取一个权限组的某一类资源权限", notes = "获取一个权限组的所有资源权限", response = String.class)
    @ApiImplicitParam(value = "resourceType",allowableValues = "1--deployment,2--grpc proxy ,3--trainset")
    @PostMapping("/getPermissions")
    public String getPermissions(@RequestBody AuthPmGpVO vo){
        if(StringUtils.isEmpty(vo.getPgid())){
            return ResultUtil.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        return ResultUtil.result(SysConf.SUCCESS,service.getPermissions(vo));
    }




    /**
     * 权限组用户增删改
     * @param userIds
     * @param pgid
     * @return
     */

    @ApiOperation(value = "权限组批量添加用户", notes = "权限组批量添加用户", response = String.class)
    @PostMapping("/addUserBatch")
    public String addUserBatch(@RequestParam("userIds")List<String>userIds,@RequestParam("pgid")String pgid) {
        return ResultUtil.result(SysConf.SUCCESS,userFkService.saveBatch(userIds,pgid, SQLConf.ADD));
    }


    @ApiOperation(value = "权限组批量删除用户", notes = "权限组批量删除用户", response = String.class)
    @PostMapping("/deleteUserBatch")
    public String deleteUserBatch(@RequestParam("userIds")List<String>userIds,@RequestParam("pgid")String pgid) {
        return ResultUtil.result(SysConf.SUCCESS,userFkService.saveBatch(userIds,pgid, SQLConf.DELETE));
    }



}


