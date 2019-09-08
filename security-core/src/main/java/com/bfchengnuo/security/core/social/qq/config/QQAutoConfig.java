package com.bfchengnuo.security.core.social.qq.config;

import com.bfchengnuo.security.core.properties.QQProperties;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.social.qq.connect.QQConnectionFactory;
import com.bfchengnuo.security.core.social.spring.SocialAutoConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ 登陆到自动配置类
 * 目的为将 {@link QQConnectionFactory} 配置到 Spring 容器中
 *
 * 当配置文件中配置了 appId 才会生效
 *
 * @author 冰封承諾Andy
 * @date 2019-09-08
 */
@Configuration
@ConditionalOnProperty(prefix = "lxl.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();

        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
