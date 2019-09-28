package com.bfchengnuo.security.app.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * OAuth 认证服务器配置
 *
 * @author 冰封承諾Andy
 * @date 2019-09-22
 */
@Configuration
@EnableAuthorizationServer
public class CustomizeAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

}
