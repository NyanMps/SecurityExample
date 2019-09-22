package com.bfchengnuo.security.browser;

import com.bfchengnuo.security.browser.session.CustomizeExpiredSessionStrategy;
import com.bfchengnuo.security.browser.session.CustomizeInvalidSessionStrategy;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器环境下扩展点配置，
 * 配置在这里的 bean，业务系统都可以通过声明同类型或同名的 bean 来覆盖
 *
 * @author 冰封承諾Andy
 * @date 2019-09-22
 */
@Configuration
public class BrowserSecurityBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomizeInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomizeExpiredSessionStrategy(securityProperties);
    }


}
