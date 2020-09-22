package main.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import main.bean.XdsCluster;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 19:24
 */
@Mapper
public interface xdsMapper {
    /**
     * route
     */
    //get max cluster version
    @Select("select * from xds_cluster order by version desc limit 1")
    public XdsCluster getMaxVersionCluster();
}
