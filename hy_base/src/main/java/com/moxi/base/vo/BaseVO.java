package com.moxi.base.vo;


import com.moxi.base.validator.annotion.IdValid;
import com.moxi.base.validator.annotion.NotBlank;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

/**
 * BaseVO   view object 表现层 基类对象
 *
 */

/**
 * @author hzh
 * @since 2020-08-07
 */
@Data
public class BaseVO<T> extends PageInfo<T> {

    /**
     * 唯一UID
     */
    @NotBlank(groups = {Update.class, Delete.class})
    private String uid;

    private Integer status;
}
