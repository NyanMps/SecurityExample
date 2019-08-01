package com.bfchengnuo.security.browser.authentication;

import com.bfchengnuo.security.core.properties.LoginType;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆成功后的行为，默认为跳转回原来的地址
 * 根据配置文件选择执行那种方式
 *
 * 也可以选择实现 {@link AuthenticationSuccessHandler} 的方式来定制;
 * 默认规则为 {@link SavedRequestAwareAuthenticationSuccessHandler}
 *
 * @author Created by 冰封承諾Andy on 2019/7/22.
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private SecurityProperties securityProperties;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String redirectUrl = null;
        if (savedRequest != null) {
            redirectUrl = savedRequest.getRedirectUrl();
        }

        // 当访问非 html 并且设置为 JSON 类型是返回 JSON 格式用户信息
        if (!StringUtils.endsWithIgnoreCase(redirectUrl, ".html")
                && LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }
    }
}
