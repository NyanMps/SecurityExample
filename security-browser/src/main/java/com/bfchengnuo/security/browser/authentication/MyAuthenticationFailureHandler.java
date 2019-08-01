package com.bfchengnuo.security.browser.authentication;

import com.bfchengnuo.security.browser.support.SimpleResponse;
import com.bfchengnuo.security.core.properties.LoginType;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登陆失败后处理逻辑
 * 可以实现 {@link AuthenticationFailureHandler} 进行自定义；
 * 默认实现： {@link SimpleUrlAuthenticationFailureHandler}
 *
 * @author Created by 冰封承諾Andy on 2019/7/22.
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
