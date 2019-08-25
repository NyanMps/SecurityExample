package com.bfchengnuo.security.demo.web.filter;

import com.bfchengnuo.security.demo.web.config.WebConfig;

import javax.servlet.*;
import java.io.IOException;

/**
 * 使用过滤器记录服务请求时间
 *
 * 如有条件，可以使用 @Component 注解，当无法使用 @Component 注解时，
 * 可以参考 {@link WebConfig} 的配置
 *
 * @see com.bfchengnuo.security.demo.web.interceptor.TimeInterceptor
 * @see com.bfchengnuo.security.demo.web.aspect.TimeAspect
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TimeFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();
        System.out.println("TimeFilter-耗时：" + (end - start));
    }

    @Override
    public void destroy() {
        System.out.println("TimeFilter destroy");
    }
}
