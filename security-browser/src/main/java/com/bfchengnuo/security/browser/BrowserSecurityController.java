package com.bfchengnuo.security.browser;

import com.bfchengnuo.security.browser.support.SimpleResponse;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理登陆请求，判断来自的客户端类型
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@RestController
public class BrowserSecurityController {
    /**
     * SE 在认证时会将原请求缓存进 requestCache
     */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /**
     * 重定向工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Resource
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，执行此方法
     * @return 如果是浏览器请求，重定向到认证页面；如果是其他返回 401 状态码提示
     */
    @RequestMapping("/auth")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse reqAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要授权");
    }
}
