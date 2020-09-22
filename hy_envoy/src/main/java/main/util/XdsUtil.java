package main.util;

import main.entity.*;
import main.entity.listener.EnvoyFilter;
import main.entity.listener.EnvoyFilterConfig;
import main.entity.listener.EnvoyListener;
import main.global.SysConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 0:15
 */
@Component
public class XdsUtil {
    public EnvoyCluster buildClusterItem(String proxy_id,String cluster_ip,int port){
        String clusterKey=(SysConf.CLUSTER+proxy_id).toLowerCase();
        //1.LbEnvoyEndpoints
        //1.EnvoyEndpoints
        EnvoyEndpoint envoyEndpoint=new EnvoyEndpoint(new EnvoyEndpoint.EnvoyEndpointAddress(new EnvoyEndpoint.EnvoyEndpointSocketAddress(new EnvoyEndpoint.EnvoyEndpointAddressSocket(cluster_ip,port))));
        List<EnvoyEndpoint> endpoints=new ArrayList<>();
        endpoints.add(envoyEndpoint);
        //2.LbEnvoyEndpoints
        List<EnvoyLoadAssignment.LbEnvoyEndpoint> lbEnvoyEndpointList=new ArrayList<>();
        lbEnvoyEndpointList.add(new EnvoyLoadAssignment.LbEnvoyEndpoint(endpoints));
        //3.EnvoyLoadAssignment
        EnvoyLoadAssignment assignment=new EnvoyLoadAssignment();
        assignment.setCluster_name(clusterKey);
        assignment.setEndpoints(lbEnvoyEndpointList);
        //4.cluster
        EnvoyCluster cluster=new EnvoyCluster.EnvoyClusterBuilder()
                .setName(clusterKey)
                .setConnect_timeout(SysConf.TIME_OUT)
                .setHttp2_protocol_options(null)
                .setLb_policy(SysConf.ROUND_ROBIN)
                .setLoad_assignment(assignment)
                .setType(SysConf.STATIC)
                .build();
        return cluster;
    }
    public EnvoyListener buildListenerConfig(String proxy_id, String cluster_ip, int port){
        //1.filters
        EnvoyFilter filter=BuildRouteConfigItem(proxy_id);
        //listener
        EnvoyListener listener=new EnvoyListener.EnvoyListenerBuilder()
                .setAddress(
                        new EnvoyListener.EnvoyEndpointSocketAddress(new EnvoyEndpoint.EnvoyEndpointAddressSocket(cluster_ip,port))
                )
                .setFilter_chains(new ArrayList<EnvoyListener.EnvoyFilters>(){{
                    add(new EnvoyListener.EnvoyFilters(new ArrayList<EnvoyFilter>(){{
                        add(filter);
                    }}));
                }})
                .setName((SysConf.CLUSTER+proxy_id).toLowerCase())
                .build();
        return listener;
    }
    public EnvoyFilter BuildRouteConfigItem(String proxy_Id){
        //1.构建EnvoyVirtualHost
        EnvoyVirtualHost host=new EnvoyVirtualHost();
        //1.1EnvoyRoutes
        //1.2EnvoyVirtualHostCors
        EnvoyVirtualHostCors cors=new EnvoyVirtualHostCors();
        cors.setAllow_headers("keep-alive,user-agent,cache-control,content-type,content-transfer-encoding,custom-header-1,x-accept-content-transfer-encoding,x-accept-response-streaming,x-user-agent,x-grpc-web,grpc-timeout,authorization");
        cors.setAllow_methods("GET, PUT, DELETE, POST, OPTIONS");
        cors.setMax_age("1728000");
        cors.setExpose_headers("custom-header-1,grpc-status,grpc-message");
        //1.2.1 EnvoyVitrualHostAllowOrigins
        List<EnvoyVirtualHostCors.EnvoyVitrualHostAllowOrigin> allowOrigins=new ArrayList<>();
        allowOrigins.add(new EnvoyVirtualHostCors.EnvoyVitrualHostAllowOrigin("*"));
        cors.setAllow_origin_string_match(allowOrigins);
        //1.3 build host
        host.setName("grpc_web_server");
        host.setDomains(new ArrayList<String>(){{add("*");}});
        host.setRoutes(new ArrayList<EnvoyRoute>(){{
            add(buildRouteItem(proxy_Id));
        }});
        host.setCors(cors);
        //1.4 hosts
        List<EnvoyVirtualHost> hostList=new ArrayList<>();
        hostList.add(host);
        //2.构建 EnvoyRouteConfig
        main.entity.listener.EnvoyRouteConfig routeConfig=new main.entity.listener.EnvoyRouteConfig();
        routeConfig.setName("ingress_web_grpc");
        routeConfig.setVirtual_hosts(hostList);
        //3.构建httpfilters
        //3.1 EnvoyFilterConfig
        EnvoyFilterConfig filterConfig=new EnvoyFilterConfig.EnvoyFilterConfigBuilder()
                .setRoute_config(routeConfig)
                .setCodec_type("AUTO")
                .setStat_prefix("ingress_http")
                .setHttp_filters(new ArrayList<EnvoyFilterConfig.filter>(){{
                    add(new EnvoyFilterConfig.filter("envoy.filters.http.cors"));
                    add(new EnvoyFilterConfig.filter("envoy.filters.http.grpc_web"));
                    add(new EnvoyFilterConfig.filter("envoy.filters.http.router"));
                }})
                .build();
        //4.构建filter
        EnvoyFilter filter=new EnvoyFilter(" envoy.filters.network.http_connection_manager",
                filterConfig);
        return filter;
    }
    public EnvoyRoute buildRouteItem(String proxy_id){
        //1 EnvoyRouteMatch
        EnvoyRoute.EnvoyRouteMatch match=new EnvoyRoute.EnvoyRouteMatch();
        match.setPrefix(SysConf.FILE_SEGMENT);
        //2 EnvoyRouteDetail
        EnvoyRoute.EnvoyRouteDetail detail=new EnvoyRoute.EnvoyRouteDetail();
        detail.setPrefix_rewrite(SysConf.FILE_SEGMENT);
        detail.setCluster((SysConf.CLUSTER+proxy_id).toLowerCase());
        detail.setMax_grpc_timeout(SysConf.GRPC_TIMEOUT);
        //3.route
        EnvoyRoute route=new EnvoyRoute.EnvoyRouteBuilder()
                .setMatch(match)
                .setRoute(detail)
                .build();
        return route;
    }
}