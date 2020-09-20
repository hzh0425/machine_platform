package com.moxi.minio.restApi;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.base.exception.ThrowableUtils;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.validator.group.Insert;
import com.moxi.commons.entity.trainSet;
import com.moxi.minio.service.MinioService;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.trainSetService;
import com.moxi.xo.vo.MenuPmVO;
import com.moxi.xo.vo.trainSetVO;
import io.minio.errors.XmlParserException;
import io.netty.util.internal.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 21:07
 */
@RestController
@RequestMapping("/file")
@Api(value = "文件相关接口", tags = {"文件相关接口"})
@Slf4j
public class fileRestApi {

    @Autowired
    trainSetService setService;
    @Autowired
    MinioService minioService;

//    @AuthorityVerify
    @ApiOperation(value = "获取文件列表", notes = "获取文件列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(trainSetVO setVO) {
        System.out.println("获取文件列表");
        return ResultUtil.result(SysConf.SUCCESS,setService.getList(setVO));
    }




//    @AuthorityVerify
    @ApiOperation(value = "删除", notes = "删除", response = String.class)
    @PostMapping("/delete")
    public String delete(@Validated({Delete.class}) @RequestBody trainSetVO setVO, BindingResult result) throws Exception {
        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        String uid=setVO.getUid();
        String userUid=setVO.getUserUid();
        /**
         * 校验身份
         */
        QueryWrapper<trainSet>wrapper=new QueryWrapper<>();
        wrapper.eq(SQLConf.UID,uid);
        trainSet set=setService.getOne(wrapper);
        if(set==null){
            return ResultUtil.result(SysConf.ERROR, MessageConf.ENTITY_NOT_EXIST);
        }else{
            if(StringUtils.equals(userUid,set.getUserUid())){
                /**
                 * 删除minio
                 */
                minioService.deleteObjects(userUid,uid);
                /**
                 * 删除数据库记录
                 */
                set.deleteById();

                return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
            }else{
                return ResultUtil.result(SysConf.ERROR,MessageConf.INVALID_AUTH);
            }
        }
    }

//    @AuthorityVerify
//    @OperationLogger(value = "")
    @ApiOperation(value = "请求训练", notes = "请求训练", response = String.class)
    @PostMapping("/trainModel")
    public String trainModel(@RequestParam("uid")String uid,@RequestParam("fid")String fid) throws XmlParserException {
        return ResultUtil.result(SysConf.SUCCESS,minioService.getList(uid,fid));
    }

    @PostMapping("/test")
    public String test(@RequestParam("uid")String uid,@RequestParam("fid")String fid) throws Exception {
        minioService.deleteObjects(uid,fid);
        return "success";
    }
}
