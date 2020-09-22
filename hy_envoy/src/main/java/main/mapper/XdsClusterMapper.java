package main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import main.bean.XdsCluster;
import org.apache.ibatis.annotations.Select;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 20:20
 */
public interface XdsClusterMapper extends BaseMapper<XdsCluster> {
    //get max cluster version
    @Select("select * from xds_cluster order by version desc limit 1")
    public XdsCluster getMaxVersionCluster();
}
