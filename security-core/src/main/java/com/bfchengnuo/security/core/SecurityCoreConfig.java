package com.bfchengnuo.security.core;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 主配置类
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 同一个密码每次生成的密文是不一样的（盐的随机）
        return new BCryptPasswordEncoder();
    }
}
