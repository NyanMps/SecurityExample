package com.bfchengnuo.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
