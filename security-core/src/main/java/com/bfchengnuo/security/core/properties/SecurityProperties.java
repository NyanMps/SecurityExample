package com.bfchengnuo.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 可配置项的总封装，包含模块有：
 * @see BrowserProperties 浏览器相关配置
 * @see ValidateCodeProperties 验证码基本配置
 *
 * @author Created by 冰封承諾Andy on 2019/7/15.
 */
@ConfigurationProperties(prefix = "lxl.security")
@Data
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();
}
