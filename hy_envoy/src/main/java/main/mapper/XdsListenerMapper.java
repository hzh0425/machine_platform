package main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import main.bean.XdsCluster;
import main.bean.XdsListener;
import org.apache.ibatis.annotations.Select;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 8:28
 */
public interface XdsListenerMapper extends BaseMapper<XdsListener> {
    //get max cluster version
    @Select("select * from xds_listener order by version desc limit 1")
    public XdsListener getMaxVersionListener();
}
