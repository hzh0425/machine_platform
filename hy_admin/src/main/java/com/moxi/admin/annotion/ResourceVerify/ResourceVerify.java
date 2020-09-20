package com.moxi.admin.annotion.ResourceVerify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/18 11:27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceVerify {
    //需要验证的资源类型
    int type() default 1;
}
