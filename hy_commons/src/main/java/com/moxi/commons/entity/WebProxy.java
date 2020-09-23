package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 21:27
 */
@TableName("micro_grpc_web_proxy")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebProxy extends Model<WebProxy> {
    @TableId(type = IdType.UUID)
    private String proxyId;
    private String deployment_id;
    private int port;
    private String cluster_ip;//内网ip
}
