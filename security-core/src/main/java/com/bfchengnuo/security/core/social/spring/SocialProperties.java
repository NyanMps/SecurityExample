package com.bfchengnuo.security.core.social.spring;

/**
 * 因为 social 项目调整，在 Spring2.x 版本中，将相关源码进行了去除，只能手动补全
 *
 * @author 冰封承諾Andy
 * @date 2019-09-08
 */
public abstract class SocialProperties {
    private String appId;
    private String appSecret;

    public SocialProperties() {
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
