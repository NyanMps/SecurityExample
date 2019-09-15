package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * 社交登陆相关配置项
 *
 * @author 冰封承諾Andy
 * @date 2019-09-08
 */
@Data
public class SocialProperties {
    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();
}
