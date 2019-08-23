package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * Browser 模块的可配置项
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Data
public class BrowserProperties {
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginResponseType loginType = LoginResponseType.JSON;

    private int rememberMeSeconds = 3600;
}
