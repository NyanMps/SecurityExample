package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码策略配置 Bean
 *
 * 当不存在实现 {@link ValidateCodeGenerator} 接口的 Bean 时，默认使用图形验证码
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 未配置时默认使用图形验证码
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(SecurityProperties securityProperties) {
        return new ImageCodeGenerator(securityProperties);
    }
}
