package com.bfchengnuo.security.core.social.wx.api;

/**
 * 微信用户信息接口
 *
 * @author 冰封承諾Andy
 * @date 2019-09-19
 */
public interface Weixin {
    /**
     * 获取微信用户的信息
     * @param openId openid
     * @return 用户信息
     */
    WeixinUserInfo getUserInfo(String openId);
}
