package com.bfchengnuo.security.core.properties;

import com.bfchengnuo.security.core.social.spring.SocialProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 使用 qq 登陆的配置
 *
 * @author 冰封承諾Andy
 * @date 2019-09-08
 */
public class QQProperties extends SocialProperties {
    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     */
    @Setter
    @Getter
    private String providerId = "qq";
}
