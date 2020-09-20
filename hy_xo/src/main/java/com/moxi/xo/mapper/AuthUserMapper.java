package com.moxi.xo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.AuthUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.security.auth.AuthPermission;
import java.util.List;
/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author hzh
 * @since 2020-09-01
 */
public interface AuthUserMapper extends SuperMapper<AuthUser> {
    /**
     * 获取用户列表
     */
    @Select("SELECT au.uid,au.nickname,au.email,au.login_date,au.login_times,au.create_date," +
            "mp.uid AS MenuGroupUid,mp.name AS MenuGroupName FROM auth_user  au \n" +
            "JOIN menu_user_pgroup_fk mu ON au.uid=mu.uid \n" +
            "JOIN menu_permission mp ON mu.mpid=mp.uid \n")
    public Page<AuthUser> getList(Page<AuthUser> page);
}
