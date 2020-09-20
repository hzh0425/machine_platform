package com.moxi.base.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * FileVO
 *
 */

/**
 * @author hzh
 * @since 2020-08-07
 */
@Data
public class FileVO extends BaseVO<FileVO> {
    /**
     * 如果是用户上传，则包含用户uid
     */
    private String userUid;

    /**
     * 如果是管理员上传，则包含管理员uid
     */
    private String adminUid;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 模块名
     */
    private String sortName;

    /**
     * 图片Url集合
     */
    private List<String> urlList;

    /**
     * 系统配置
     */
    private Map<String, Object> systemConfig;


}
