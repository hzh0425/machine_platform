package com.moxi.grpc.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 实体类
 * 爪哇笔记：https://blog.52itstyle.vip
 */
@Data
@Component
public class MinIoProperties {

    @Value(value = "${minio.bucket}")
    private String bucket;

    @Value(value = "${minio.host}")
    private String host;

    @Value(value = "${minio.url}")
    private String url;

    @Value(value = "${minio.access-key}")
    private String accessKey;

    @Value(value = "${minio.secret-key}")
    private String secretKey;
}