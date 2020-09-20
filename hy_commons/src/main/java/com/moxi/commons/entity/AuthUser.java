package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moxi.base.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author hzh
 * @since 2020-09-01
 */
@Data
@TableName("auth_user")
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser extends Model<AuthUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户UID
     */
    @TableId(value = "uid",type = IdType.UUID)
    private String uid;

    private String password;

    private String nickname;

    private String phone;

    private String email;


    private String loginIp;

    private Long loginTimes;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date loginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createDate;

    @TableField(exist = false)
    private String MenuGroupUid;

    @TableField(exist = false)
    private String MenuGroupName;
}
