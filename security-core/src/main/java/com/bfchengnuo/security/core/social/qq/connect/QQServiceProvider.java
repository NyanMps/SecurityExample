package com.bfchengnuo.security.core.social.qq.connect;

import com.bfchengnuo.security.core.social.qq.api.QQ;
import com.bfchengnuo.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 用于 qq 登陆的 Provider 实现
 * 它基本包含所有的认证逻辑，由 {@link QQOAuth2Template} 和 {@link QQImpl} 实现
 * 被 {@link QQConnectionFactory} 调用
 *
 * @see OAuth2Template 默认实现
 *
 * @author 冰封承諾Andy
 * @date 2019-09-07
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId, String appSecret) {
        // 可以使用默认实现 OAuth2Template
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        // 获取具体的实现，每次都 new 一个新的保证其内部的 accessToken 唯一
        return new QQImpl(accessToken, appId);
    }
}
