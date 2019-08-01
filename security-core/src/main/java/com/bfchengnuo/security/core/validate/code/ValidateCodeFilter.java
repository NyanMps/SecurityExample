package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义验证码的校验过滤器，位置处于验证凭证（MyUserDetailsService）之前
 *
 * 使用 {@link OncePerRequestFilter} 保证每次最多调用一次
 * 通过实现 {@link InitializingBean} 来进行 url 的初始化与装填
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private AuthenticationFailureHandler authenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private Set<String> urls = new HashSet<>();
    private SecurityProperties securityProperties;
    /**
     * spring 提供的通配符匹配工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] urlArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getValidateCode().getImageCode().getUrl(),
                ",");

        if (ArrayUtils.isNotEmpty(urlArr)) {
            urls.addAll(Arrays.asList(urlArr));
        }
        // 添加登陆请求
        urls.add("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean match = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                match = true;
                break;
            }
        }

        if (match &&
                StringUtils.equalsIgnoreCase("post", request.getMethod())) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        // 从session中拿出验证码
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(
                request,
                ValidateCodeController.SESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }
}
