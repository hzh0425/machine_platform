package com.moxi.admin.annotion.AuthorityVerify;

import com.moxi.base.global.ECode;
import com.moxi.commons.entity.MenuPm;
import com.moxi.utils.RedisUtil;
import com.moxi.utils.ResultUtil;
import com.moxi.xo.global.MessageConf;
import com.moxi.xo.global.RedisConf;
import com.moxi.xo.global.SysConf;
import com.moxi.xo.service.AuthUserService;
import com.moxi.xo.service.MenuPmService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
import java.util.List;
/**
 * 权限校验 切面实现
 *
 */

/**
 * @author hzh
 * @since 2020-08-07
 */
@Aspect
@Component
@Slf4j
public class AuthorityVerifyAspect {


    @Resource
    MenuPmService menuPermissionService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    AuthUserService authUserService;

    //切入点
    @Pointcut(value = "@annotation(authorityVerify)")
    public void pointcut(AuthorityVerify authorityVerify) {

    }

    @Around(value = "pointcut(authorityVerify)")
    public Object doAround(ProceedingJoinPoint joinPoint, AuthorityVerify authorityVerify) throws Throwable {

        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attribute.getRequest();


        //获取请求路径
        String url = request.getRequestURI();

        String adminUid="3bf3b8298cbe91d5246c511bc6659d99";

//        if(request.getAttribute(SysConf.ADMIN_UID)!=null){
//            adminUid=request.getAttribute(SysConf.ADMIN_UID).toString();
//        }else{
//            return ResultUtil.result(ECode.UNAUTHORIZED,MessageConf.INVALID_TOKEN);
//        }

        //根据uid从redis中获取权限组id
        String groupId="";
        String userGroup=RedisConf.USER_GROUP+SysConf.REDIS_SEGMENTATION+adminUid;
        if(redisUtil.hasKey(userGroup)){
            groupId=redisUtil.get(userGroup);
        }else{
            //从mysql查询该用户所对应的group组
            groupId=authUserService.getUserGroupId(adminUid);
            redisUtil.set(userGroup,groupId);
        }

        // 判断权限
        String key= RedisConf.PM_GROUP_MENU + SysConf.REDIS_SEGMENTATION + groupId;

        if(!redisUtil.hasKey(key)){
            System.out.println("get from mysql-----------");
            // redis中还没有没有该权限组的权限列表,就查询mysql数据库获取,并保存到redis set集合中
            List<MenuPm> permissionList=menuPermissionService.getMenuPermissionList(adminUid);
            String [] urls=new String[permissionList.size()];
            for (int i = 0; i <permissionList.size() ; i++) {
                urls[i]=permissionList.get(i).getUrl();
            }
            //保存权限列表到redis set集合中
            redisUtil.sAdd(key, urls);
            redisUtil.expire(key,1,TimeUnit.HOURS);
        }

        // 判断该角色是否能够访问该接口,即url是否为set集合的成员
        if(!redisUtil.sIsMember(key,url)){
            System.out.println("用户不具有操作权限，访问的路径: "+url );
            return ResultUtil.result(ECode.NO_OPERATION_AUTHORITY, MessageConf.RESTAPI_NO_PRIVILEGE);
        }else{
            System.out.println("用户具有访问权限:"+url);
        }

        //执行业务
        return joinPoint.proceed();
    }

}
