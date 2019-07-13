package com.bfchengnuo.secunity.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义请求校验器
 *
 * @author Created by 冰封承諾Andy on 2019/7/12.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyParameterValidator.class)
public @interface MyValidator {
    String message() default "default msg";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
