package com.bfchengnuo.security.demo.web.config;

import com.bfchengnuo.security.demo.web.filter.TimeFilter;
import com.bfchengnuo.security.demo.web.interceptor.TimeInterceptor;
import lombok.AllArgsConstructor;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Web 中常见配置
 *
 * 关于注入，IDEA 提示推荐使用构造方法，而不是 @Autowired
 *
 * @author Created by 冰封承諾Andy on 2019/7/14.
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TimeInterceptor timeInterceptor;

    /**
     * 配置过滤器
     */
    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean<TimeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        TimeFilter timeFilter = new TimeFilter();

        // 匹配的路径
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setFilter(timeFilter);
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 解决上传文件大于 10M 出现连接重置的问题
     */
    @Bean
    public TomcatServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 代表没有限制
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 关于异步调用的一些配置
        configurer.setDefaultTimeout(TimeUnit.SECONDS.toMillis(5));
    }
}
