package com.moxi.xo.mapper;

import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.UserPmGpFK;
import org.apache.ibatis.annotations.Select;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/4 16:46
 */
public interface UserPmGpFKMapper extends SuperMapper<UserPmGpFK> {
    /**
     * 获取用户所对应的组的uid
     */
    @Select("SELECT mpid FROM menu_user_pgroup_fk WHERE uid=#{userUid}")
    public String getUserGroupUid(String userUid);
}
