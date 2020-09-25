package com.moxi.xo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/26 0:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroDeploymentStatus {
    public String name;
    public String imageName;
    public String description;
    public int replicas;
    public Date createTime;
    public String status;
}
