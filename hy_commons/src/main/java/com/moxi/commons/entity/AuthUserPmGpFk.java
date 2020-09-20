package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:38
 * 用户-资源组 中间表
 */
@TableName("auth_user_pgroup_fk")
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserPmGpFk {
    private String uid;//用户uid
    private String pgid;//资源组id
}
