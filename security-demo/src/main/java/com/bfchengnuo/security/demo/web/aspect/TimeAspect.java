package com.bfchengnuo.security.demo.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * 自定义切面配置
 *
 * @see com.bfchengnuo.security.demo.web.filter.TimeFilter
 * @see com.bfchengnuo.security.demo.web.interceptor.TimeInterceptor
 *
 * @author 冰封承諾Andy
 * @date 2019-08-25
 */
// @Aspect
// @Component
public class TimeAspect {
    @Around("execution(* com.bfchengnuo.security.demo.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("time aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is "+arg);
        }

        long start = System.currentTimeMillis();

        Object object = pjp.proceed();

        System.out.println("time aspect 耗时:"+ (System.currentTimeMillis() - start));

        System.out.println("time aspect end");

        return object;
    }
}
