package com.moxi.xo.service;

import com.moxi.base.service.SuperService;
import com.moxi.commons.entity.WebProxy;
import java.util.List;
/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/23 21:29
 */
public interface WebProxyService extends SuperService<WebProxy> {
   public List<WebProxy> getList();

   public String add(String deployment_id, int port);

   public String delete(String proxy_id);
}
