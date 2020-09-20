package com.moxi.xo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 16:01
 */
@Data
public class AuthPmVO {
    public String pid;
    public String name;
    public String url;
    public String  method;
    public Date create_date;
    public String scope;
    public String owner;

}
