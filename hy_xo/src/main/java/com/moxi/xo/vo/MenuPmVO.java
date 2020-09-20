package com.moxi.xo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moxi.base.vo.BaseVO;
import com.moxi.commons.entity.MenuPm;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/4 13:19
 */
@Data
public class MenuPmVO extends BaseVO<MenuPmVO> {



    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单级别 （一级分类，二级分类）
     */
    private Integer menuLevel;

    /**
     * 菜单类型 （菜单，按钮）
     */
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





}
