package com.bfchengnuo.security.core;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 主配置类
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
