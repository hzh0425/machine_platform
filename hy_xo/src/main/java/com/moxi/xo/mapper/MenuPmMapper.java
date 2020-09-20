package com.moxi.xo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.MenuPm;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/2 10:11
 */
public interface MenuPmMapper extends SuperMapper<MenuPm> {
    /**
     * 获取用户的权限菜单
     * @param uid
     * @return
     */
      @Select("SELECT m3.* FROM auth_user au JOIN menu_user_pgroup_fk m0 \n" +
              "ON au.uid=m0.uid\n" +
              "JOIN  menu_permission_group m1 \n" +
              "ON m0.mpid=m1.uid\n" +
              "JOIN  menu_permission_group_fk m2\n" +
              "ON m1.uid=m2.mpgid\n" +
              "JOIN menu_permission m3\n" +
              "ON m2.mpid=m3.uid\n" +
              "WHERE m0.uid=#{uid}")
    public   List<MenuPm> getMenuPermissionList(@Param("uid")String uid);




    /**
     * 获取所有菜单
     */
    @Select("SELECT * FROM menu_permission WHERE menu_level=1 order by sort Desc")
    @Results(value = {
            @Result(column = "uid",property = "uid",id = true),
            @Result(column = "uid" ,property = "childMenu",many = @Many(select = "com.moxi.xo.mapper.MenuPmMapper.getChildMenu"))
    })
    public List<MenuPm> getAllList();
    /**
     * 获取所有菜单,分页
     */
    @Select("SELECT * FROM menu_permission WHERE menu_level=1 order by sort Desc")
    @Results(value = {
            @Result(column = "uid",property = "uid",id = true),
            @Result(column = "uid" ,property = "childMenu",many = @Many(select = "com.moxi.xo.mapper.MenuPmMapper.getChildMenu"))
    })
    public Page<MenuPm> getAllList(Page<MenuPm> page);


    /**
     * 获取二级菜单,用于mybatis关联查询
     */
    @Select("SELECT * FROM menu_permission WHERE menu_level=2 and parent_uid=#{parent_uid} order by " +
            "sort desc")
    @Results(value = {
            @Result(column = "uid",property = "uid",id = true),
            @Result(column = "uid" ,property = "childMenu",many = @Many(select = "com.moxi.xo.mapper.MenuPmMapper.getButtonMenu"))
    })
    public List<MenuPm> getChildMenu(String parent_uid);


    /**
     * 获取所有的二级菜单,和其下的按钮
     */
    @Select("SELECT * FROM menu_permission WHERE menu_level=2  order by sort desc")
    @Results(value = {
            @Result(column = "uid",property = "uid",id = true),
            @Result(column = "uid" ,property = "childMenu",many = @Many(select = "com.moxi.xo.mapper.MenuPmMapper.getButtonMenu"))
    })
    public Page<MenuPm> getAllChildMenu(Page<MenuPm> page);

    /**
     * 获取三级按钮,用于mybatis关联查询
     */
    @Select("SELECT * FROM menu_permission WHERE menu_level=3 and parent_uid=#{parent_uid} order by " +
            "sort desc")
    public List<MenuPm> getButtonMenu(String parent_uid);

    /**
     * 获取权限的uid
     */



}
