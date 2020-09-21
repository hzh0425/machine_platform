package main.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 17:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyHostRoute {
    @JsonProperty("@type")
    public String _type="type.googleapis.com/envoy.api.v2.RouteConfiguration";
    public String name;
    public EnvoyVirtualHosts virtual_hosts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EnvoyVirtualHosts{
        List<EnvoyVirtualHost> list;
    }

}
