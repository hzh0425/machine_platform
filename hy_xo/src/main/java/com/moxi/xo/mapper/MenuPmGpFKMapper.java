package com.moxi.xo.mapper;

import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.MenuPmGpFK;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:18
 */
public interface MenuPmGpFKMapper extends SuperMapper<MenuPmGpFK> {
    @Select("SELECT * FROM menu_permission_group_fk WHERE mpgid=#{uid}")
    List<String> getGroupPmIds(@Param("uid")String uid);
}
