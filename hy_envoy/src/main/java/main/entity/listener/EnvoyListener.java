package main.entity.listener;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.EnvoyEndpoint;

import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyListener {
    @JsonProperty("@type")
    public String _type="type.googleapis.com/envoy.api.v2.Listener";
    public String name;
    public EnvoyEndpointSocketAddress address;
    public List<EnvoyFilters> filter_chains;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyFilters{
        public List<EnvoyFilter> filters;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyEndpointSocketAddress{
        public EnvoyEndpoint.EnvoyEndpointAddressSocket socket_address;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyEndpointAddressSocket{
        public String address;
        public int port_value;
    }

    public static class EnvoyListenerBuilder{
        @JsonProperty("@type")
        public String _type="type.googleapis.com/envoy.api.v2.Listener";
        public String name;
        public EnvoyEndpointSocketAddress address;
        public List<EnvoyFilters> filter_chains;

        public EnvoyListenerBuilder set_type(String _type) {
            this._type = _type;
            return this;
        }

        public EnvoyListenerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public EnvoyListenerBuilder setAddress(EnvoyEndpointSocketAddress address) {
            this.address = address;
            return this;
        }

        public EnvoyListenerBuilder setFilter_chains(List<EnvoyFilters> filter_chains) {
            this.filter_chains = filter_chains;
            return this;
        }
        public EnvoyListener build(){
            return new EnvoyListener(this);
        }

    }
    public EnvoyListener(EnvoyListenerBuilder b){
        this(b._type,b.name,b.address,b.filter_chains);
    }
}
