package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 14:26
 * 用户资源权限表
 */
@Data
@TableName("auth_permission")
public class AuthPm extends Model<AuthPm> {
    @TableId
    private String pid;
    private String name;
    private String url;
    private String method;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date create_date;
    private String scope;
    private String owner;
    private String resourceId;
    private int resourceType;
}
