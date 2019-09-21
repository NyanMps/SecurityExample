package com.bfchengnuo.security.core.properties;

import com.bfchengnuo.security.core.social.spring.SocialProperties;
import lombok.Data;

/**
 * 微信登陆的配置相关
 *
 * @author 冰封承諾Andy
 * @date 2019-09-19
 */
@Data
public class WeixinProperties extends SocialProperties {
    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";
}
