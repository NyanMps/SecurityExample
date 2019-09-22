package com.bfchengnuo.security.browser.logout;

import com.bfchengnuo.security.browser.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了 lxl.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回 json 格式的响应。
 *
 * @author 冰封承諾Andy
 * @date 2019-09-22
 */
@Slf4j
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    private String signOutSuccessUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomizeLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("退出成功");

        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }

    }
}
