package com.moxi.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 20:37
 */
@Data
@TableName("user_trainset")
public class trainSet extends Model<trainSet> {
    @TableId(type = IdType.UUID)
    private String uid;

    private String userUid;

    private String fileName;
    private Long  fileSize;
    private int chunkSize;
    private String url;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
}
