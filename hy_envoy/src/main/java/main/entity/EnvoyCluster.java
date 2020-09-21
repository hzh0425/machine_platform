package main.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 16:47
 */

/**
 * version_info: "0"
 * resources:
 * - "@type": type.googleapis.com/envoy.api.v2.Cluster
 *   name: some_service
 *   connect_timeout: 0.25s
 *   lb_policy: ROUND_ROBIN
 *   type: EDS
 *   eds_cluster_config:
 *     eds_config:
 *       api_config_source:
 *         api_type: GRPC
 *         cluster_names: [xds_cluster]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyCluster {
    @JsonProperty("@type")
    public String _type="type.googleapis.com/envoy.api.v2.Cluster";
    public String name;

    public String connect_timeout;

    public String type;

    public Object http2_protocol_options;

    public String lb_policy;

    public EnvoyLoadAssignment load_assignment;
}
