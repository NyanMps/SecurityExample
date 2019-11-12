package com.bfchengnuo.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter 后处理器，用于在不同环境下个性化社交登录的配置
 * 适应 App 登陆后 SDK 返回授权码而非 openid 的情况
 *
 * @author 冰封承諾Andy
 * @date 2019-11-10
 */
public interface SocialAuthenticationFilterPostProcessor {
    /**
     * 具体处理逻辑
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
