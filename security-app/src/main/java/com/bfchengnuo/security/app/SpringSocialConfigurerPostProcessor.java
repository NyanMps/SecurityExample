package com.bfchengnuo.security.app;

import com.bfchengnuo.security.core.social.SocialConfig;
import com.bfchengnuo.security.core.social.support.CustomizeSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后处理器，适配 App 无 Session 下自动完成绑定
 * 实现 {@link BeanPostProcessor} 接口到作用是，在 Spring 初始化所有 bean 之前和之后会调用我们自定义到方法逻辑
 *
 * @see com.bfchengnuo.security.app.social.AppSingUpUtils
 * @see SocialConfig#springSocialSecurityConfig()
 *
 * @author 冰封承諾Andy
 * @date 2019-11-16
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "springSocialSecurityConfig")) {
            CustomizeSpringSocialConfigurer configurer = (CustomizeSpringSocialConfigurer) bean;
            // 改变跳转到 URL
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
