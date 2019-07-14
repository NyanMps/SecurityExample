package com.bfchengnuo.secunity.demo.web.config;

import com.bfchengnuo.secunity.demo.web.filter.TimeFilter;
import com.bfchengnuo.secunity.demo.web.interceptor.TimeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

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
}
