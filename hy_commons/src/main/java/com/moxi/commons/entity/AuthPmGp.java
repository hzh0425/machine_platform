package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 14:27
 * 资源权限,用户组表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_permission_group")
public class AuthPmGp extends Model<AuthPmGp> {
    @TableId(type = IdType.UUID)
    private String pgid;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
    private String description;


}
