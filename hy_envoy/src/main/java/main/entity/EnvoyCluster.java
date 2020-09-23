package main.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    public String type="logical_dns";

    public http2_options http2_protocol_options;

    public String lb_policy;

    public String dns_lookup_family;

    public EnvoyLoadAssignment load_assignment;



    public EnvoyCluster(EnvoyClusterBuilder b){
        this.name=b.name;
        this.connect_timeout=b.connect_timeout;
        this.type=b.type;
        this.http2_protocol_options=b.http2_protocol_options;
        this.lb_policy=b.lb_policy;
        this.load_assignment=b.load_assignment;
        this.dns_lookup_family=b.dns_lookup_family;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyClusterBuilder{
        @JsonProperty("@type")
        public String _type="type.googleapis.com/envoy.api.v2.Cluster";
        public String name;

        public String connect_timeout;

        public String type;

        public http2_options http2_protocol_options;

        public String lb_policy;

        public String dns_lookup_family;

        public EnvoyLoadAssignment load_assignment;


        public EnvoyClusterBuilder setDns_lookup_family(String dns_lookup_family) {
            this.dns_lookup_family = dns_lookup_family;
            return this;
        }

        public EnvoyClusterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EnvoyClusterBuilder setConnect_timeout(String connect_timeout) {
            this.connect_timeout = connect_timeout;
            return this;
        }

        public EnvoyClusterBuilder setType(String type) {
            this.type = type;
            return this;
        }


        public EnvoyClusterBuilder setHttp2_protocol_options(http2_options http2_protocol_options) {
            this.http2_protocol_options = http2_protocol_options;
            return this;
        }

        public EnvoyClusterBuilder setLb_policy(String lb_policy) {
            this.lb_policy = lb_policy;
            return this;
        }


        public EnvoyClusterBuilder setLoad_assignment(EnvoyLoadAssignment load_assignment) {
            this.load_assignment = load_assignment;
            return this;
        }

        public EnvoyCluster build(){
            return new EnvoyCluster(this);
        }
    }

    @Data
    @NoArgsConstructor
    public static class http2_options implements Serializable {
        String option;
    }

}
