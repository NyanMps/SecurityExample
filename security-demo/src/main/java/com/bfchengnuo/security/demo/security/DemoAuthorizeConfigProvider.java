package com.bfchengnuo.security.demo.security;

import com.bfchengnuo.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author 冰封承諾Andy
 * @date 2019-11-22
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user/register", "/social/signUp")
                // 以上匹配不需要认证
                .permitAll();

        config.antMatchers("/demo.html")
                .hasRole("ADMIN");
    }
}
