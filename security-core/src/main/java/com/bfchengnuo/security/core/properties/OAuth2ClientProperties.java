package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-17
 */
@Data
public class OAuth2ClientProperties {
    private String clientId;

    private String clientSecret;

    private int accessTokenValidateSeconds = 7200;
}
