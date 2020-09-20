package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moxi.base.entity.SuperEntity;
import lombok.Data;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 14:45
 * 权限组
 */
@Data
@TableName("menu_permission_group")
public class MenuPmGp extends SuperEntity<MenuPmGp> {
    private String name;
    private String summary;

    /**
     * permission ids;
     */
    @TableField(exist = false)
    private List<String> pmIds;


}
