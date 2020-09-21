package main.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/21 17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvoyVirtualHost {
   public String name;
   public List<String> domains;
   public EnvoyRoutes routes;
   public EnvoyVirtualHostCors cors;

   @Data
   @AllArgsConstructor
   @NoArgsConstructor
   public static class EnvoyRoutes{
       List<EnvoyRoute> list;
   }


}
