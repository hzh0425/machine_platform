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



    public static class EnvoyRouteBuilder{
        public EnvoyRouteMatch match;
        public EnvoyRouteDetail route;

        public EnvoyRouteBuilder setMatch(EnvoyRouteMatch match) {
            this.match = match;
            return this;
        }

        public EnvoyRouteBuilder setRoute(EnvoyRouteDetail route) {
            this.route = route;
            return this;
        }

        public EnvoyRoute build(){
            return new EnvoyRoute(this);
        }
    }

    public EnvoyRoute(EnvoyRouteBuilder b){
        this.match=b.match;
        this.route=b.route;
    }

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
    }
}
