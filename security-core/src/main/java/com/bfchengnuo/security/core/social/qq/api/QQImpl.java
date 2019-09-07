package com.bfchengnuo.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 所有的 Api 实现类都要继承 {@link AbstractOAuth2ApiBinding}
 * 每一个用户都有一个独立的实现类对象，在 {@link AbstractOAuth2ApiBinding accessToken} 的 accessToken 来保存令牌信息
 *
 * @author 冰封承諾Andy
 * @date 2019-09-01
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // 使用参数传递，而不是用默认的请求头
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        // 用 accessToken 换取 openid
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    @Override
    public QQUserInfo getUserInfo4QQ() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        QQUserInfo userInfo;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
