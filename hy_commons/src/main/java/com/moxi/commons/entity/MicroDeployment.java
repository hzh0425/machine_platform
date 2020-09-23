package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 0:33
 */
@Data
@TableName("micro_deployment")
@AllArgsConstructor
@NoArgsConstructor
public class MicroDeployment extends Model<MicroDeployment> {
    @TableId
    private String deploymentId;
    private String description;
    private String name;
    private Date createDate;
    private String imageName;
    private int replicas;
    private String ports;
    private String nspace;
    private String env;
    private int type;
    private int sessionAffinity;
    private String command;
    private String clusterIp;
    private String owner;
}
