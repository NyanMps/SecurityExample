package com.bfchengnuo.security.core.authentication.mobile;

import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Created by 冰封承諾Andy on 2019/8/2.
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    @Setter
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(smsCodeAuthenticationToken.getPrincipal()));

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 构造新的已认证的 token 实体
        SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationTokenResult.setDetails(smsCodeAuthenticationToken.getDetails());
        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
