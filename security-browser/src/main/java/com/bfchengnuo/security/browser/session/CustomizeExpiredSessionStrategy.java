package com.bfchengnuo.security.browser.session;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 并发登陆导致 session 失效时，默认的处理逻辑
 *
 * @author 冰封承諾Andy
 * @date 2019-09-21
 */
public class CustomizeExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public CustomizeExpiredSessionStrategy(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
