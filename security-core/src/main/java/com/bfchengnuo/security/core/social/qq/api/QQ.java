package com.bfchengnuo.security.core.social.qq.api;

/**
 * 用于处理 QQ 登陆 API 的相关
 *
 * @author 冰封承諾Andy
 * @date 2019-09-01
 */
public interface QQ {
    /**
     * 通过 API 获取 qq 返回的用户信息
     * @return 用户信息对象
     */
    QQUserInfo getUserInfo4QQ();
}
