package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.properties.SecurityConstants;
import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.processor.ValidateCodeProcessorHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义验证码的校验过滤器，位置处于验证凭证（MyUserDetailsService）之前
 *
 * 使用 {@link OncePerRequestFilter} 保证每次最多调用一次
 * 通过实现 {@link InitializingBean} 来进行 url 的初始化与装填
 *
 * PS：
 * sessionStrategy（new HttpSessionSessionStrategy()）工具类的使用方法：
 * sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     * 失败处理
     */
    private AuthenticationFailureHandler authenticationFailureHandler;
    private SecurityProperties securityProperties;
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 需要进行校验验证码的 url 集合
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    /**
     * spring 提供的通配符匹配工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 构造方法进行 IOC 注入
     */
    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler,
                              SecurityProperties securityProperties,
                              ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }

    /**
     * 初始化加载要拦截的 url 列表
     *
     * 除了配置文件中获取的 url，还需要添加必要的登陆 URL（手机验证码和图形验证码）
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getValidateCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getValidateCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的 URL 根据校验的类型放入 map
     *
     * @param urlString 自定义验证的 URL，使用逗号分割
     * @param type 进行验证的类型，短信 or 图形
     */
    private void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                // 验证码校验
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回 null
     *
     * 过滤非 Get 的请求，然后判断请求的 URL 是否匹配到配置文件中需要认证的 URL；
     * 如果匹配到，则返回此匹配的 URL 对应的 ValidateCodeType；
     * 按照顺序，返回的是最后一个匹配成功的。
     *
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "GET")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

}
