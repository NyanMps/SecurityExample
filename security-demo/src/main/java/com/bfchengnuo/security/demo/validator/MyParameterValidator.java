package com.bfchengnuo.security.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器的具体实现
 * 继承自 ConstraintValidator，会自动加入 IOC 容器，不需要注解标识
 *
 * @author Created by 冰封承諾Andy on 2019/7/12.
 */
public class MyParameterValidator implements ConstraintValidator<MyValidator, Object> {
    @Override
    public void initialize(MyValidator constraintAnnotation) {
        System.out.println("MyParameterValidator init...");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("参数为：" + value);
        return false;
    }
}
