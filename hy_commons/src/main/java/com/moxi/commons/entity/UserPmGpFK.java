package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/4 15:58
 * 用户 权限组 中间表
 */
@Data
@TableName("menu_user_pgroup_fk")
public class UserPmGpFK extends Model<UserPmGpFK> {
    /**
     * 用户id
     */
    @TableId(type = IdType.UUID)
    private String uid;
    /**
     * 权限组id
     */
    private String mpid;
}
