package com.bfchengnuo.security.core.social.qq.connect;

import com.bfchengnuo.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 默认情况下，所有的 /auth 都会被 {@link org.springframework.social.security.SocialAuthenticationFilter} 拦截，根据 ConnectionFactory 的 providerId 进行匹配
 * 例如 /auth/qq 就会匹配到 {@link QQConnectionFactory} 处理
 *
 * @see com.bfchengnuo.security.core.social.qq.config.QQAutoConfig
 *
 * @author 冰封承諾Andy
 * @date 2019-09-07
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
