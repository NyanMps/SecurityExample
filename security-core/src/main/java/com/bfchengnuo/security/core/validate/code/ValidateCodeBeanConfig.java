package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.image.ImageCodeGenerator;
import com.bfchengnuo.security.core.validate.code.sms.DefaultSmsCodeSenderImpl;
import com.bfchengnuo.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码策略配置 Bean
 *
 * 当不存在实现 {@link ValidateCodeGenerator} 接口的 Bean 时，默认使用图形验证码
 * 未实现默认的 {@link SmsCodeSender}
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 未配置时默认使用图形验证码
     */
    @Bean
    @ConditionalOnMissingBean(ImageCodeGenerator.class)
    public ValidateCodeGenerator imageCodeGenerator(SecurityProperties securityProperties) {
        return new ImageCodeGenerator(securityProperties);
    }

    /**
     * 未配置短信发送实现时，使用默认实现
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSenderImpl();
    }
}
