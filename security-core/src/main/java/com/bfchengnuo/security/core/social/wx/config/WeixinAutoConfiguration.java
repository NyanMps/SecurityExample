package com.bfchengnuo.security.core.social.wx.config;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.properties.WeixinProperties;
import com.bfchengnuo.security.core.social.spring.SocialAutoConfigurerAdapter;
import com.bfchengnuo.security.core.social.view.CustomizeConnectView;
import com.bfchengnuo.security.core.social.wx.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信 OAuth2 登陆的自动配置
 *
 * @author 冰封承諾Andy
 * @date 2019-09-19
 */
@Configuration
@ConditionalOnProperty(prefix = "lxl.security.social.wx", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWx();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    /**
     * 配置默认的绑定(weixinConnected)与解绑后(weixinConnect)跳转到的视图
     * @return 默认视图
     */
    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new CustomizeConnectView();
    }
}
