package com.bfchengnuo.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 自定义 template 来处理 QQ 授权通过后的响应
 * 默认 QQ 返回的是 text 格式，social 不支持
 *
 * 执行 exchangeForAccess 方法用 密钥 交换令牌的时候，会执行 {@link OAuth2Template#postForAccessGrant(String, MultiValueMap)}
 * 但是它不支持 text 类型的响应，所以，只能我们自己重写 OAuth2Template
 *
 * @see org.springframework.social.security.provider.OAuth2AuthenticationService#getAuthToken(HttpServletRequest, HttpServletResponse)
 * @see com.bfchengnuo.security.core.social.qq.api.QQImpl 获取 openid 也就是 code 的逻辑，获取 openid 需要本类提供 accessToken
 * @see QQServiceProvider 负责调用其执行
 *
 * @author 冰封承諾Andy
 * @date 2019-09-07
 */
public class QQOAuth2Template extends OAuth2Template {
    /**
     * 默认当拼装参数是符合 OAuth 规范当，不需要更改
     * 当 useParametersForClientAuthentication 为 true 时，才会带上 client_id 和 client_secret
     *
     * 构造方法传入 URL，构建获取 AuthorizationCode 和 accessToken 的 URL
     * 参考 {@link OAuth2Template##buildAuthUrl(String, GrantType, OAuth2Parameters)}
     * 当执行 {@link org.springframework.social.security.provider.OAuth2AuthenticationService#getAuthToken(HttpServletRequest, HttpServletResponse)} 没有拿到 code 时，
     * 就会调用构造执行获取 AuthorizationCode 当过程，否则执行获取 accessToken 当过程；
     * 使用抛出异常的形式将构造的 URL 抛出，被 {@link org.springframework.social.security.SocialAuthenticationFilter} 拿到后进行处理。
     *
     * 拼装完成后会调用 postForAccessGrant 进行后处理
     *
     * @see OAuth2Template#exchangeForAccess(String, String, MultiValueMap)
     */
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 处理自定义的响应内容，拼装规范的 AccessGrant 对象，也就是获取 accessToken 的过程
     * 默认规则是从 json 中获取信息填充
     *
     * 这里，我们自己发送请求，并处理响应
     *
     * @see OAuth2Template#createAccessGrant(String, String, String, Long, Map)
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加处理 text 的转换器
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
