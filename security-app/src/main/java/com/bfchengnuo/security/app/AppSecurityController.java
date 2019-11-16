package com.bfchengnuo.security.app;

import com.bfchengnuo.security.app.social.AppSingUpUtils;
import com.bfchengnuo.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理 App 第三方信息登陆
 * 大体流程：
 * 用户使用第三方登陆，在数据库中没有查到相关绑定信息，于是重定向到 /social/signUp
 * 并且保存信息到 redis 中，返回相关第三方信息。
 * 客户端根据返回到信息提示用户注册或者绑定，然后请求 /register 完成绑定
 *
 * @see SpringSocialConfigurerPostProcessor
 * @author 冰封承諾Andy
 * @date 2019-11-16
 */
@RestController
public class AppSecurityController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSingUpUtils appSingUpUtils;

    /**
     * 参考 BrowserSecurityController#buildSocialUserInfo
     * @param request
     * @return
     */
    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());

        appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

        return userInfo;
    }
}
