package com.moxi.minio.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 9:12
 */
@Data
@ApiModel("大文件分片入参实体")
public class MultipartFileParam {
    @ApiModelProperty("文件传输任务ID")
    private String taskId;

    @ApiModelProperty("当前为第几分片")
    private int chunkNumber;

    @ApiModelProperty("每个分块的大小")
    private long chunkSize;


    @ApiModelProperty("分片总数")
    private int totalChunks;

    @ApiModelProperty("文件唯一标识")
    private String identifier;


    @ApiModelProperty("分块文件传输对象")
    private MultipartFile file;
}
