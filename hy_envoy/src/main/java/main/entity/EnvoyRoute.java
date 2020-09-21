package main.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyRoute {

    public EnvoyRouteMatch match;
    public EnvoyRouteDetail route;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyRouteMatch{
        public String prefix;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyRouteDetail{
        public String prefix_rewrite;

        public String cluster;

        public String max_grpc_timeout;
    }
}
