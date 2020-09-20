package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moxi.base.validator.annotion.IntegerNotNull;
import com.moxi.base.validator.annotion.NotBlank;
import com.moxi.base.validator.group.Insert;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:08
 * 菜单权限表
 */
@Data
@TableName("menu_permission")
public class MenuPm extends Model<MenuPm> implements Comparable<MenuPm> {


   @TableId(type = IdType.UUID)
   private String uid;


   /**
    * 菜单名称
    */
   @NotBlank(groups = {Insert.class, Update.class})
   private String name;

   /**
    * 菜单级别 （一级分类，二级分类）
    */
   @IntegerNotNull(groups = {Insert.class, Update.class})
   private Integer menuLevel;

   /**
    * 菜单类型 （菜单，按钮）
    */
   @IntegerNotNull(groups = {Insert.class, Update.class})
   private Integer menuType;

    /**
     * Icon图标
     */
    private String icon;

    /**
     * 父UID
     */
    private String parentUid;

    /**
     * URL地址
     */
    private String url;

    /**
     * 排序字段(越大越靠前)
     */
    private Integer sort;


    /**
     * 状态 0：失效  1：生效
     */
    private int status;

    /**
     * @TableField 配置需要填充的字段
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


   @Override
   public int compareTo(@NotNull MenuPm o) {
      return o.getSort()-this.sort;
   }

   /**
    * 以下不存在表中
    */
   @TableField(exist = false)
   private List<MenuPm> childMenu;

}
