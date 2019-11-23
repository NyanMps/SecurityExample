package com.bfchengnuo.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * SpringSecurity 配置的封装
 *
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
public interface AuthorizeConfigProvider {
    /**
     * 实际是 HttpSecurity#authorizeRequests 返回的对象
     * 拿着这个对象我们可以继续链式调用 antMatchers 来进行个性化配置
     *
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
