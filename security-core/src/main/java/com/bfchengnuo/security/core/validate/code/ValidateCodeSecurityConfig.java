package com.bfchengnuo.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

/**
 * 基本验证的配置（图形验证码）
 * 主要作用为在前端加入相应到过滤器进行处理验证码
 *
 * 短信验证码可参考 {@link com.bfchengnuo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig}
 *
 * 继承自 SecurityConfigurerAdapter，可以在 BrowserSecurityConfig 调用 apply 进行应用配置
 *
 * @author 冰封承諾Andy
 * @date 2019-08-21
 */
@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private Filter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
