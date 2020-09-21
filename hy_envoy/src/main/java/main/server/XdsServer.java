package main.server;

import main.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 20:01
 */
@Service
public class XdsServer {
   AtomicInteger integer=new AtomicInteger(0);
   public EnvoyRouteConfig GrpcRouteConfig;
   public  EnvoyClustersConfig GrpcClusterConfig;
   public List<EnvoyRoute> envoyRouteList;
   public final static String route_type="type.googleapis.com/envoy.api.v2.RouteConfiguration";
   @PostConstruct
   public void init(){
       this.envoyRouteList=new ArrayList<>();
       this.GrpcRouteConfig=initRouteConfig();
       this.GrpcClusterConfig=initClusterConfig();
   }

    /**
     * 初始化route config
     * @return
     */
   public EnvoyRouteConfig initRouteConfig(){
       //1.构建EnvoyVirtualHost
          EnvoyHostRoute.EnvoyVirtualHosts hosts=new EnvoyHostRoute.EnvoyVirtualHosts();
          EnvoyVirtualHost host=new EnvoyVirtualHost();
          //1.1EnvoyRoutes
          EnvoyVirtualHost.EnvoyRoutes routes=new EnvoyVirtualHost.EnvoyRoutes();
          routes.setList(envoyRouteList);
          //1.2EnvoyVirtualHostCors
          EnvoyVirtualHostCors cors=new EnvoyVirtualHostCors();
          cors.setAllow_headers("keep-alive,user-agent,cache-control,content-type,content-transfer-encoding,custom-header-1,x-accept-content-transfer-encoding,x-accept-response-streaming,x-user-agent,x-grpc-web,grpc-timeout,authorization");
          cors.setAllow_methods("GET, PUT, DELETE, POST, OPTIONS");
          cors.setMax_age("1728000");
          //1.2.1 EnvoyVitrualHostAllowOrigins
          List<EnvoyVirtualHostCors.EnvoyVitrualHostAllowOrigin> allowOrigins=new ArrayList<>();
          allowOrigins.add(new EnvoyVirtualHostCors.EnvoyVitrualHostAllowOrigin("*"));
          cors.setAllow_origin_string_match(allowOrigins);
          //1.3 build host
          host.setName("grpc_web_server");
          host.setDomains(new ArrayList<String>());
          host.setRoutes(routes);
          host.setCors(cors);
          //1.4 hosts
          List<EnvoyVirtualHost> hostList=new ArrayList<>();
          hostList.add(host);
          hosts.setList(hostList);
       //2.构建EnvoyHostRoute
          EnvoyHostRoute hostRoute=new EnvoyHostRoute();
          hostRoute.setName("ingress_web_grpc");
          hostRoute.setVirtual_hosts(hosts);
       //3. 构建route resources
          List<EnvoyHostRoute> routeList=new ArrayList<>();
          routeList.add(hostRoute);
       //4. 构建route config
          EnvoyRouteConfig config=new EnvoyRouteConfig();
          config.setVersion_info("0");
          config.setResources(routeList);
          return config;
   }
   public EnvoyClustersConfig initClusterConfig(){

       return new EnvoyClustersConfig("0",new EnvoyClustersConfig.EnvoyClusters(new ArrayList<>()));
   }
    /**
     * 增加代理
     */
    public boolean AddGrpcWebVirtualHostRoute(String proxy_id,String cluster_ip,int port){
        EnvoyRoute routeItem = null;
        EnvoyCluster clusterItem = null;
        boolean routeFlag=false;
        boolean clusterFlag=false;
        String version=integer.getAndAdd(1)+"";
        this.GrpcRouteConfig.version_info=version;
        this.GrpcClusterConfig.version_info=version;
        //构建route
        routeItem=buildRouteItem(proxy_id);
        clusterItem=buildClusterItem(proxy_id,cluster_ip,port);
        this.envoyRouteList.add(routeItem);
        this.GrpcClusterConfig.resources.getList().add(clusterItem);
        return true;
    }
    public EnvoyRoute buildRouteItem(String proxy_id){
        //1 EnvoyRouteMatch
        EnvoyRoute.EnvoyRouteMatch match=new EnvoyRoute.EnvoyRouteMatch();
        match.setPrefix(("/grpc_web/"+proxy_id+"/").toLowerCase());
        //2 EnvoyRouteDetail
        EnvoyRoute.EnvoyRouteDetail detail=new EnvoyRoute.EnvoyRouteDetail();
        detail.setPrefix_rewrite("/");
        detail.setCluster(("cluster-"+proxy_id).toLowerCase());
        detail.setMax_grpc_timeout("0s");
        //3.route
        EnvoyRoute route=new EnvoyRoute();
        route.setMatch(match);
        route.setRoute(detail);
        return route;
    }
    public EnvoyCluster buildClusterItem(String proxy_id,String cluster_ip,int port){
        //1.LbEnvoyEndpoints
        //1.EnvoyEndpoints
        EnvoyEndpoint envoyEndpoint=new EnvoyEndpoint(new EnvoyEndpoint.EnvoyEndpointAddress(new EnvoyEndpoint.EnvoyEndpointSocketAddress(new EnvoyEndpoint.EnvoyEndpointAddressSocket(cluster_ip,port))));
        List<EnvoyEndpoint> endpoints=new ArrayList<>();
        endpoints.add(envoyEndpoint);
        //2.LbEnvoyEndpoints
        List<EnvoyLoadAssignment.LbEnvoyEndpoint> lbEnvoyEndpointList=new ArrayList<>();
        lbEnvoyEndpointList.add(new EnvoyLoadAssignment.LbEnvoyEndpoint(endpoints));
        EnvoyLoadAssignment.LbEnvoyEndpoints lbEnvoyEndpoints=new EnvoyLoadAssignment.LbEnvoyEndpoints();
        lbEnvoyEndpoints.setList(lbEnvoyEndpointList);
        //3.EnvoyLoadAssignment
        EnvoyLoadAssignment assignment=new EnvoyLoadAssignment();
        assignment.setCluster_name(("cluster-"+proxy_id).toLowerCase());
        assignment.setEndpoints(lbEnvoyEndpoints);
        //4.cluster
        EnvoyCluster cluster=new EnvoyCluster();
        cluster.setName(("cluster-"+proxy_id).toLowerCase());
        cluster.setConnect_timeout("0.5s");
        cluster.setType("static");
        cluster.setHttp2_protocol_options(new Object());
        cluster.setLb_policy("round_robin");
        cluster.setLoad_assignment(assignment);
        return cluster;
    }
}
