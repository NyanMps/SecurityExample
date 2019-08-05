package com.bfchengnuo.security.browser;

import com.bfchengnuo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.SmsCodeFilter;
import com.bfchengnuo.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * SpringSecurity 配置
 * <p>
 * 自定义认证规则可以将 {@link UserDetailsService} 的实现注入到 IOC，
 * 或者通过 {@link WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)} 的重载进行配置
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private DataSource dataSource;
    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 同一个密码每次生成的密文是不一样的（盐的随机）
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 基于 JDBC 的 “记住我” 实现
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // jdbcTokenRepository.setCreateTableOnStartup(true);

        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(authenticationFailureHandler, securityProperties);
        // 初始化需要验证的 url set 集合
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(authenticationFailureHandler, securityProperties);
        smsCodeFilter.afterPropertiesSet();

        // 增加自定义的验证码校验过滤器
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 使用表单登陆
                .formLogin()
                // 跳转认证的页面(默认 /login)
                .loginPage("/auth")
                // 进行认证的请求地址（UsernamePasswordAuthenticationFilter）
                .loginProcessingUrl("/login")
                // 自定义登陆成功、失败后的处理逻辑
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(Math.toIntExact(TimeUnit.HOURS.toSeconds(securityProperties.getBrowser().getRememberMeHour())))
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                // 放行必要的页面
                .antMatchers("/auth",
                        "/code/*",
                        "/login",
                        "/login/sms",
                        securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     //使用自定义认证规则，并且使用 BCrypt 算法处理密码
    //     auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    // }
}
