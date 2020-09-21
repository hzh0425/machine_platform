package main.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 16:40
 */

/**
 * endpoint 类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyEndpoint {
    public EnvoyEndpointAddress endpoint;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyEndpointAddress{
        public EnvoyEndpointSocketAddress address;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyEndpointSocketAddress{
        public EnvoyEndpointAddressSocket socket_address;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyEndpointAddressSocket{
        public String address;
        public int port_value;
    }
}
