package com.moxi.xo.vo;



import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/16 19:41
 */
@Data
public class DeploymentServiceVO {

    public String deployment_id ;
    /// <summary>
    /// 部署描述
    /// </summary>
    /// <value></value>
    public String description ;
    /// <summary>
    /// 创建的时间
    /// </summary>
    /// <value></value>
    public Date create_date;
    /// <summary>
    /// 镜像名
    /// </summary>
    /// <value></value>
    public String image_name ;
    /// <summary>
    /// 部署人
    /// </summary>
    /// <value></value>
    public String owner ;
    /// <summary>
    /// 部署名称
    /// </summary>
    /// <value></value>
    public String name ;
    /// <summary>
    /// 命名空间
    /// </summary>
    /// <value></value>
    public String namespace;
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
    /// Json 格式的端口
    /// </summary>
    /// <value></value>
    public String ports ;
    /// <summary>
    /// 环境变量
    /// </summary>
    /// <value></value>

    public List<EnvironmentVariableTypeVO> envList ;
    /// <summary>
    /// Json格式的Env
    /// </summary>
    /// <value></value>
    public String env ;
    /// <summary>
    /// 集合ip
    /// </summary>
    /// <value></value>
    public String cluster_ip ;
    /// <summary>
    /// 启动命令，可以在镜像内存放脚本后启动
    /// </summary>
    /// <value></value>
    public String command ;

    public MicroServiceTypeVO type;

    public int session_affinity=0;

    public int currentPage=0;
    public int pageSize=10;
}
