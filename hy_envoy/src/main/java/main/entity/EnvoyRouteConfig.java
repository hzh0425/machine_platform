package main.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyRouteConfig {
    public String version_info;
    public List<EnvoyHostRoute> resources;
}
