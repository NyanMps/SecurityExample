package com.bfchengnuo.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自定义 SpringSocialConfigurer
 *
 * 自定义社交登陆的前缀 url，默认 /auth
 *
 * @see org.springframework.social.security.provider.OAuth2AuthenticationService
 *
 * @author 冰封承諾Andy
 * @date 2019-09-10
 */
public class CustomizeSpringSocialConfigurer extends SpringSocialConfigurer {
    private String filterProcessesUrl;

    public CustomizeSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
