package com.moxi.xo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/25 22:54
 */
@Data
public class DeploymentVO {




    public String description ;
    /// <summary>
    /// 镜像名
    /// </summary>
    /// <value></value>
    @ApiModelProperty(required = true,notes = "镜像名称")
    public String image_name ;
    /// <summary>
    /// 部署名称
    /// </summary>
    /// <value></value>
    public String name ;
    /// <summary>
    /// 副本数
    /// </summary>
    /// <value></value>
    public int replicas ;
    /// <summary>
    /// 格式转化
    /// </summary>
    /// <value></value>
    public List<PortTypeVO> portList;
    /// <summary>
    /// 环境变量
    /// </summary>
    /// <value></value>
    public List<EnvironmentVariableTypeVO> envList ;
    /// <summary>
    /// 启动命令，可以在镜像内存放脚本后启动
    /// </summary>
    /// <value></value>
    public List<String> command ;


    public int session_affinity=0;
}
