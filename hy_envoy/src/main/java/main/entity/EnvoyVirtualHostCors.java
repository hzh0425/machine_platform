package main.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyVirtualHostCors {
    public List<EnvoyVitrualHostAllowOrigin> allow_origin_string_match;

    public String allow_methods;

    public String allow_headers;

    public String max_age;

    public String expose_headers;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnvoyVitrualHostAllowOrigin{
        public String prefix;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EnvoyHttpFilter{
        public String name;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EnvoyHttpFilterConfig{
        public EnvoyHttpService http_service;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EnvoyHttpService{
        public EnvoyHttpServiceUri service_uri;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EnvoyHttpServiceUri{
        public String uri;

        public String cluster;

        public String timeout;
    }

}
