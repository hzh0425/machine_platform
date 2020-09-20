package com.moxi.xo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.moxi.base.entity.SuperEntity;
import com.moxi.base.validator.annotion.NotBlank;
import com.moxi.base.validator.group.GetList;
import com.moxi.base.vo.BaseVO;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/9 20:43
 */
@Data
public class trainSetVO extends BaseVO<trainSetVO> {

    @NotBlank(groups = {GetList.class,Delete.class})
    private String userUid;
    private String fileName;
    private int chunkSize;
    private Long fileSize;
    private String url;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
}
