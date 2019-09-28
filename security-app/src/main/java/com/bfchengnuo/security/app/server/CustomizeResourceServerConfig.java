package com.bfchengnuo.security.app.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * OAuth 资源服务器配置
 *
 * @author 冰封承諾Andy
 * @date 2019-09-22
 */
@Configuration
@EnableResourceServer
public class CustomizeResourceServerConfig extends ResourceServerConfigurerAdapter {

}
