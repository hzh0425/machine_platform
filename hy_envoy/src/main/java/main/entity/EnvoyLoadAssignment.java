package main.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 16:49
 */

/**响应格式:
 * version_info: "0"
 * resources:
 * - "@type": type.googleapis.com/envoy.api.v2.ClusterLoadAssignment
 *   cluster_name: some_service
 *   endpoints:
 *   - lb_endpoints:
 *     - endpoint:
 *         address:
 *           socket_address:
 *             address: 127.0.0.2
 *             port_value: 1234
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyLoadAssignment {

    public String cluster_name;

    public  List<LbEnvoyEndpoint> endpoints;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LbEnvoyEndpoint{
        public List<EnvoyEndpoint> lb_endpoints;
    }

}
