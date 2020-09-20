package com.moxi.admin.annotion.ResourceVerify;

import com.moxi.admin.annotion.AuthorityVerify.AuthorityVerify;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * @author hzh
 * @version 1.0
 * @date 2020/9/18 11:29
 */
@Service
@Aspect
public class ResourceVerifyAspect {

    //切入点
    @Pointcut(value = "@annotation(resourceVerify)")
    public void pointcut(ResourceVerify resourceVerify) {

    }

    @Around(value = "pointcut(resourceVerify)")
    public Object doAround(ProceedingJoinPoint joinPoint, ResourceVerify resourceVerify) throws Throwable {
        //1.获取用户的uid

        //2.获取请求的url路径

        //3.获取用户所在权限组pgid集合,借助redis缓存

        //4.从各个权限组的url set集合的并集中,看当前请求url是否为其成员

        //5.不是,则返回forbidden

        return joinPoint.proceed();
    }
}
