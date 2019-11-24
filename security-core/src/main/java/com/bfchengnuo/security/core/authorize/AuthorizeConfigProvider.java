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
     * @return 返回的 boolean 表示配置中是否有针对 anyRequest 的配置。
     * 在整个授权配置中，应该有且仅有一个针对 anyRequest 的配置，如果所有的实现都没有针对 anyRequest 的配置，
     * 系统会自动增加一个 anyRequest().authenticated() 的配置。如果有多个针对 anyRequest 的配置，则会抛出异常。
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
