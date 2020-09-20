package com.moxi.grpc.bean;

import lombok.Data;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/12 17:13
 */
@Data
public class minioFile {
    private long size;
    private int currentChunk;
    private String path;
}
