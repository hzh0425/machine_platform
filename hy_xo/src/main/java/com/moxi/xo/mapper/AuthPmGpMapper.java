package com.moxi.xo.mapper;

import com.moxi.base.mapper.SuperMapper;
import com.moxi.commons.entity.AuthPm;
import com.moxi.commons.entity.AuthPmGp;
import org.apache.ibatis.annotations.Select;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 15:40
 */
public interface AuthPmGpMapper extends SuperMapper<AuthPmGp> {
    /**
     * 获取所有权限id
     */
//    @Select("SELECT a2.resourceId,a2.url FROM \n" +
//            "`auth_permission_group_fk` a1 JOIN `auth_permission` a2 ON a1.pid=a2.pid \n" +
//            "JOIN ${resource_table} a3 ON a2.resourceId=a3.${resourceId} \n" +
//            "WHERE a1.pgid=#{pgid} AND a2.resourceType=${resourceType} ")
    @Select("SELECT a2.resourceId,a2.url,a2.resourceType FROM `auth_permission_group_fk` \n" +
            "a1 JOIN `auth_permission` a2 \n" +
            "ON a1.pid=a2.pid \n" +
            "WHERE a1.pgid=#{pgid} "
            )
    public List<AuthPm> getGroupPermissionAll(String pgid);


    /**
     * 获取某种类型的资源id
     * @param pgid
     * @param resourceType
     * @return
     */
    @Select("SELECT a2.resourceId,a2.url FROM \n" +
            "`auth_permission_group_fk` a1 JOIN `auth_permission` a2 ON a1.pid=a2.pid \n" +
            "WHERE a1.pgid=#{pgid} AND a2.resourceType=${resourceType} ")
    public List<AuthPm> getGroupPermissionWithType(String pgid,int resourceType);

}
