package com.moxi.xo.vo;

import io.kubernetes.client.custom.IntOrString;
import lombok.Data;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/16 19:43
 */
@Data
public class PortTypeVO {
    public int port;
    public int cluster_port;
}
