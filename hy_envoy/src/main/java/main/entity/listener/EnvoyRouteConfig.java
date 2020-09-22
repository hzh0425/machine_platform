package main.entity.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.EnvoyVirtualHost;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 22:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyRouteConfig {
    public String name;
    public List<EnvoyVirtualHost> virtual_hosts;
}
