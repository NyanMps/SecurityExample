package com.bfchengnuo.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 扩展 SpringSecurityOAuth 的登陆方式
 * <p>
 * 只需要调用 {@link org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices} 就可以产生令牌了，
 * 而构建它需要 OAuth2Authentication 对象，也就是 OAuth2Request 和 Authentication。
 * OAuth2Request 可以从请求中的信息来组装；
 *
 * 这样，就可以用之前表单认证的地址，用用户名与密码来进行获取类 OAuth 流程的 token 了，记得在头里加上客户端信息就好
 * <p>
 * 使用工具类来解析 Base64 的客户端信息，参考 {@link org.springframework.security.web.authentication.www.BasicAuthenticationFilter}
 * <p>
 * 基于浏览器的 MyAuthenticationSuccessHandler 修改
 *
 * @author Created by 冰封承諾Andy on 2019/7/22.
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无 client 信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配:" + clientId);
        }

        /*
         * 正常情况下，第一个参数的 map 需要传递认证流程所需要的参数，以便于验证和产生 Authentication，
         * 但是在现在的自定义流程中 Authentication 已经直接传递进来了，就不需要 map 了
         * 最后一个参数是认证方式，这里是自定义
         */
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        // 获取令牌
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));
    }

    /**
     * 解码头部 basic 认证信息
     *
     * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter#extractAndDecodeHeader(String, HttpServletRequest)
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
