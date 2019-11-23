package com.bfchengnuo.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 负责管理和装配 {@link AuthorizeConfigProvider} 对象
 *
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
public interface AuthorizeConfigManager {
    /**
     * 使个性化配置生效
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
