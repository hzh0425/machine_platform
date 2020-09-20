package com.moxi.kube.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.commons.entity.MicroDeployment;
import com.moxi.kube.global.SqlConf;
import com.moxi.kube.util.KubeUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.SQLConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.MicroDeploymentService;
import com.moxi.xo.vo.DeploymentServiceVO;
import io.kubernetes.client.openapi.ApiException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/17 0:29
 */
@Service
public class KubeService {
    @Autowired
    KubeUtil kubeUtil;
    @Autowired
    MicroDeploymentService deploymentService;
    public String DeployMicroService(DeploymentServiceVO service){
        //1.部署服务
        Map<String,Object> deployResult=kubeUtil.DeployMicroService(service);
        //2.判断,构建entity,插入数据库
        String code=deployResult.get(SysConf.CODE).toString();
        if(code.equals(SysConf.SUCCESS)){
            MicroDeployment deployment=new MicroDeployment(service.getDeployment_id(),service.getDescription(),service.getName(),service.getCreate_date(),service.getImage_name(),service.getReplicas(),service.getPorts(),service.getNamespace(),service.getEnv(),1,service.getSession_affinity(),service.getCommand(),service.getCluster_ip(),service.getOwner());
            deployment.insert();
            return ResultUtil.result(SysConf.SUCCESS,MessageConf.INSERT_SUCCESS);
        }else{
            return ResultUtil.result(SysConf.ERROR, MessageConf.INSERT_FAIL);
        }
    }

    public String deleteMicroService(String deployment_id,String uid) throws ApiException {
        //查看mysql中是否有该记录
        QueryWrapper<MicroDeployment> wrapper=new QueryWrapper<>();
        wrapper.eq(SqlConf.OWNER,uid);
        wrapper.eq(SqlConf.DEPLOYMENT_ID,deployment_id);
        MicroDeployment deployment=deploymentService.getOne(wrapper);
        if(deployment==null)return ResultUtil.result(SysConf.ERROR,MessageConf.ENTITY_NOT_EXIST);
        //删除微服务部署
        kubeUtil.RemoveMicroDeployment(deployment_id);
        //删除mysql记录
        deployment.deleteById();
        return ResultUtil.result(SysConf.SUCCESS,MessageConf.DELETE_SUCCESS);
    }

    public IPage<MicroDeployment> getList(DeploymentServiceVO serviceVO){
        QueryWrapper<MicroDeployment> wrapper=new QueryWrapper<>();
        wrapper.eq(SqlConf.OWNER,serviceVO.owner);
        Page<MicroDeployment> page=new Page<>(serviceVO.getCurrentPage(),serviceVO.getPageSize());
        IPage<MicroDeployment> page1 = deploymentService.page(page, wrapper);
        System.out.println("11111");
        System.out.println(wrapper.getSqlComment());
        return page1;
    }
}
