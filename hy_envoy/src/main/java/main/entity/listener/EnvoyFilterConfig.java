package main.entity.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.entity.EnvoyVirtualHost;

import javax.servlet.http.HttpFilter;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/22 22:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyFilterConfig {
    private String stat_prefix;
    private String codec_type;
    private EnvoyRouteConfig route_config;
    private List<filter> http_filters;


    public  static class EnvoyFilterConfigBuilder{
        private String stat_prefix;
        private String codec_type;
        private EnvoyRouteConfig route_config;
        private List<filter> http_filters;

        public EnvoyFilterConfigBuilder setCodec_type(String codec_type) {
            this.codec_type = codec_type;
           return this;
        }

        public EnvoyFilterConfigBuilder setHttp_filters(List<filter> http_filters) {
            this.http_filters = http_filters;
            return this;
        }

        public EnvoyFilterConfigBuilder setRoute_config(EnvoyRouteConfig route_config) {
            this.route_config = route_config;
            return this;
        }

        public EnvoyFilterConfigBuilder setStat_prefix(String stat_prefix) {
            this.stat_prefix = stat_prefix;
            return this;
        }
        public EnvoyFilterConfig build(){
            return new EnvoyFilterConfig(this);
        }
    }
    public EnvoyFilterConfig(EnvoyFilterConfigBuilder b){
        this(b.stat_prefix,b.codec_type,b.route_config,b.http_filters);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class filter{
        private String name;
    }
}
