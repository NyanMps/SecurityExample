package com.bfchengnuo.security.core.authentication;

import com.bfchengnuo.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 浏览器与App模块公用的配置类
 *
 * @author 冰封承諾Andy
 * @date 2019-08-21
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        // 使用表单登陆
        http.formLogin()
                // 跳转认证的页面(默认 /login)
                .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
                // 进行认证的请求地址（UsernamePasswordAuthenticationFilter）
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 自定义登陆成功、失败后的处理逻辑
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }
}
