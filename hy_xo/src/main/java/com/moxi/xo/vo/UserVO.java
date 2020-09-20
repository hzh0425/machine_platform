package com.moxi.xo.vo;

import com.moxi.base.validator.annotion.NotBlank;
import com.moxi.base.validator.group.Insert;
import com.moxi.base.vo.BaseVO;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 21:01
 */
@Data
public class UserVO extends BaseVO<UserVO> {
    @NotBlank(groups = {Update.class, Insert.class})
    private String nickName;

    @NotBlank(groups = {Insert.class})
    private String password;

    private String phone;


    private String email;

    private int isRememberMe=0;

    /**
     * 所在权限组的Uid
     */
    private String MenuGroupUid;
    /**
     * 所在资源组的uid
     */
    private String resourceGroupId;
}
