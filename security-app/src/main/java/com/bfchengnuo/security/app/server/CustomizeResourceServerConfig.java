package com.bfchengnuo.security.app.server;

import com.bfchengnuo.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.bfchengnuo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.bfchengnuo.security.core.authorize.AuthorizeConfigManager;
import com.bfchengnuo.security.core.properties.SecurityConstants;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * OAuth 资源服务器配置
 *
 * @author 冰封承諾Andy
 * @date 2019-09-22
 */
@Configuration
@EnableResourceServer
public class CustomizeResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    /**
     * 社交第三方登陆的配置类
     */
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    /**
     * 用于 App 使用 openid 进行第三方登陆
     */
    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 使用表单登陆
        http.formLogin()
                // 跳转认证的页面(默认 /login)
                .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
                // 进行认证的请求地址（UsernamePasswordAuthenticationFilter）
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                // 自定义登陆成功、失败后的处理逻辑
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);

        http.apply(validateCodeSecurityConfig).and()
                .apply(smsCodeAuthenticationSecurityConfig).and()
                .apply(springSocialConfigurer).and()
                .apply(openIdAuthenticationSecurityConfig).and()
                .csrf().disable();

        // 设置权限
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
