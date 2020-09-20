package com.moxi.xo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.MenuPmGp;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/3 14:49
 */
public interface MenuPmGpMapper extends SuperMapper<MenuPmGp> {
    /**
     * 获取权限组列表
     */
    @Select("select * from menu_permission_group where status=1 order by create_time desc")
//    @Results({
//            @Result(column = ,property = ,many = @Many())
//    })
    public Page<MenuPmGp> getList();
}
