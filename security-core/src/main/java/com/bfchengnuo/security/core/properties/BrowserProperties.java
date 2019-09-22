package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * Browser 模块的可配置项
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@Data
public class BrowserProperties {
    /**
     * 登录页面，当引发登录行为的 url 以 html 结尾时，会跳到这里配置的 url 上
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 登录响应的方式，默认是 json
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * '记住我'功能的有效时间，默认1小时
     */
    private int rememberMeSeconds = 3600;

    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = "/signUp.html";

    /**
     * 退出成功时跳转的 url，如果配置了，则跳到指定的 url，如果没配置，则返回 json 数据。
     */
    private String signOutUrl;

    /**
     * session 管理配置项
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     * 只在 signInResponseType 为 REDIRECT 时生效
     */
    private String singInSuccessUrl;
}
