package com.bfchengnuo.security.demo.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 自定义校验异常
 *
 * @author 冰封承諾Andy
 * @date 2019-08-25
 */
@Data
@AllArgsConstructor
public class ValidateException extends RuntimeException {
    private List<ObjectError> errors;
}
