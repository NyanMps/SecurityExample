package com.bfchengnuo.security.core.social.support;

import lombok.Data;

/**
 * 用户社交信息封装的实体
 *
 * @author 冰封承諾Andy
 * @date 2019-09-17
 */
@Data
public class SocialUserInfo {
    /**
     * 客户端标识
     */
    private String providerId;

    /**
     * openID
     */
    private String providerUserId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headimg;
}
