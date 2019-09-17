package com.bfchengnuo.security.browser;

import com.bfchengnuo.security.browser.support.SimpleResponse;
import com.bfchengnuo.security.core.properties.SecurityConstants;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.social.support.SocialUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理登陆请求，判断来自的客户端类型
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@RestController
public class BrowserSecurityController {
    /**
     * SE 在认证时会将原请求缓存进 requestCache
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 重定向工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，执行此方法
     * @return 如果是浏览器请求，重定向到认证页面；如果是其他返回 401 状态码提示
     */
    @RequestMapping(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse reqAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要授权");
    }

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request http 请求对象
     * @return 用户社交信息对象
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connection);
    }

    /**
     * 根据 Connection 信息构建 SocialUserInfo
     */
    private SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
        SocialUserInfo userInfo = new SocialUserInfo();
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }
}
