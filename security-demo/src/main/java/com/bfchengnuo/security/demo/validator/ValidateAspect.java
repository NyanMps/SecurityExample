package com.bfchengnuo.security.demo.validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.validation.BindingResult;

/**
 * 示例：用于处理 RESTful 请求异常的切面定义
 *
 * @author 冰封承諾Andy
 * @date 2019-08-25
 */
// @Aspect
// @Component
public class ValidateAspect {

    @Around("execution(* com.bfchengnuo.security.demo.web.controller.UserController.*(..))")
    public Object handleValidateResult(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("进入切片");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult) {
                BindingResult errors = (BindingResult)arg;
                if (errors.hasErrors()) {
                    throw new ValidateException(errors.getAllErrors());
                }
            }
        }

        return pjp.proceed();
    }
}
