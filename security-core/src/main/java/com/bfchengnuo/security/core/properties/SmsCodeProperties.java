package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * 短信验证码配置类
 *
 * @author 冰封承諾Andy
 * @date 2019-08-19
 */
@Data
public class SmsCodeProperties {
    private int length = 6;
    private int expireIn = 60;

    private String url;
}
