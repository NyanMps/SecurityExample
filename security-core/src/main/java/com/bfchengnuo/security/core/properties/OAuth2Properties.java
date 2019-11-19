package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-17
 */
@Data
public class OAuth2Properties {
    /**
     * JWT 的签名，密签与验签的时候用，配置文件配置，这里是默认值
     */
    private String jwtSigningKey = "lxl";
    private OAuth2ClientProperties[] clients = {};
}
