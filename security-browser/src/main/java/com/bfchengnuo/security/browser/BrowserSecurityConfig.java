package com.bfchengnuo.security.browser;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * SpringSecurity 配置
 *
 * 自定义认证规则可以将 {@link org.springframework.security.core.userdetails.UserDetailsService} 的实现注入到 IOC，
 * 或者通过 {@link WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder)} 的重载进行配置
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Configuration
@AllArgsConstructor
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityProperties securityProperties;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 同一个密码每次生成的密文是不一样的（盐的随机）
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用表单登陆
        http.formLogin()
                // 跳转认证的页面(默认 /login)
                .loginPage("/auth")
                // 进行认证的请求地址（UsernamePasswordAuthenticationFilter）
                .loginProcessingUrl("/login")
                // 自定义登陆成功、失败后的处理逻辑
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                // 放行必要的页面
                .antMatchers("/auth", securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     //使用自定义认证规则，并且使用 BCrypt 算法处理密码
    //     auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    // }
}
