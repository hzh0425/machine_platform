package com.moxi.minio.restApi;

import com.moxi.commons.config.jwt.JwtHelper;
import com.moxi.minio.entity.MultipartFileParam;
import com.moxi.minio.global.SysConf;
import com.moxi.minio.service.MinioService;

import com.moxi.utils.RedisUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.utils.ServerInfo.Sys;
import com.moxi.xo.service.trainSetService;
import com.moxi.xo.vo.trainSetVO;
import io.minio.errors.XmlParserException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/8 19:56
 */
@RestController
@RequestMapping("/upload")
@Api(value = "训练集相关接口", tags = {"训练集相关接口"})
@Slf4j
public class UploadRestApi {
    @Autowired
    MinioService minioService;

    @Autowired
    trainSetService trainSetService;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    RedisUtil redisUtil;


    @ApiOperation(value = "分块上传", notes = "分块上传", response = String.class)
    @PostMapping("/upload")
    public void upload( MultipartFileParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取用户uid
        String token=request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            response.setStatus(401);
            response.getWriter().print(SysConf.TOKEN_INVALID);
            response.getWriter().flush();
        }else{
            String uid=jwtHelper.getUserUid(token,SysConf.base64);
            //2.根据file identifier,获取存储在redis中的file uuid
            String key=SysConf.FILE_IDENTIFIER+param.getIdentifier();
            String fileUid="";
            if(redisUtil.hasKey(key)){
                fileUid=redisUtil.get(key);
            }else{
                fileUid= UUID.randomUUID().toString();
                redisUtil.set(key,fileUid,30, TimeUnit.MINUTES);
            }
            //3.传输该分块
            String fileName=fileUid+SysConf.FILE_PARTITION+SysConf.PART+param.getChunkNumber();
            String url=minioService.putObject(param,uid,fileName);
            //4.如果当前是最后一块,则更新数据库
            if(param.getChunkNumber()==param.getTotalChunks()){
                /**
                 * 增加mysql记录
                 */
                trainSetVO setVO=new trainSetVO();
                setVO.setChunkSize(param.getTotalChunks());
                setVO.setUid(fileUid);
                setVO.setUserUid(uid);
                setVO.setFileSize(param.getChunkSize()*param.getTotalChunks());
                setVO.setFileName(param.getFile().getOriginalFilename());
                setVO.setUrl(fileName);
                trainSetService.add(setVO);
            }
            System.out.println("end"+param.getChunkNumber());
            response.setStatus(200);
            response.getWriter().print("上传成功");
            response.getWriter().flush();
        }
    }


    @ApiOperation(value ="获取文件的所有分片", notes = "获取文件的所有分片", response = String.class)
    @PostMapping("/getFile")
    public String getFile(@RequestParam("uid")String uid,@RequestParam("fileId")String fileId) throws XmlParserException {
        System.out.println(uid);
        System.out.println(fileId);
        return ResultUtil.result("success",minioService.getList(uid,fileId+ SysConf.FILE_PARTITION+SysConf.PART));
    }



}
