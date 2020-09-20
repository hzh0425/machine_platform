package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:16
 * 权限表-组 中间表
 */
@Data
@TableName("menu_permission_group_fk")
public class MenuPmGpFK extends Model<MenuPmGpFK> {
    /**
     * menu_permission id
     */
    private String mpid;
    /**
     * menu_permission_group id
     */
    private String mpgid;
}
