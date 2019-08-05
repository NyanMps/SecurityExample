package com.bfchengnuo.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基本验证码属性
 *
 * @author Created by 冰封承諾Andy on 2019/8/2.
 */
@Data
@AllArgsConstructor
public class ValidateCode {
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn4Second) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn4Second);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
