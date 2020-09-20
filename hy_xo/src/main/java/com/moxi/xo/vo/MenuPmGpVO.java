package com.moxi.xo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moxi.base.validator.annotion.NotBlank;
import com.moxi.base.validator.group.Insert;
import com.moxi.base.vo.BaseVO;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 15:06
 */
@Data
public class MenuPmGpVO extends BaseVO<MenuPmGpVO> {
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    private String summary;

    //menu permission ids
    private String mpids;

    @NotBlank(groups = {Update.class})
    private Boolean changePermission;
}
