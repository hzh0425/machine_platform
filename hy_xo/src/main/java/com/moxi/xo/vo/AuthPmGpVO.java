package com.moxi.xo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moxi.base.validator.annotion.NotBlank;
import com.moxi.base.validator.group.Insert;
import com.moxi.base.vo.BaseVO;
import com.moxi.base.vo.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 16:02
 */
@ApiModel("权限组相关接收参数实体类")
@Data
public class AuthPmGpVO extends PageInfo<AuthPmGpVO> {
    @NotBlank(groups = {Delete.class})
    @ApiModelProperty(name = "权限组id")
    private String pgid;


    @NotBlank(groups = {Insert.class})
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date create_date;

    @ApiModelProperty(name = "权限组描述")
    private String description;

    @ApiModelProperty(name = "权限组所拥有的权限类型",value = "1 deployment,2 grpc web proxy,3 trainset ")
    private int permissionType;
}
