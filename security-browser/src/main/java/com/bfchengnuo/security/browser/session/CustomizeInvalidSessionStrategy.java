package com.bfchengnuo.security.browser.session;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的 session 失效处理策略
 *
 * @author 冰封承諾Andy
 * @date 2019-09-21
 */
public class CustomizeInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CustomizeInvalidSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

}
