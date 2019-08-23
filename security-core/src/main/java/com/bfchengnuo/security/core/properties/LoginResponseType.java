package com.bfchengnuo.security.core.properties;

/**
 * 登陆后如何处理的选择
 *
 * @author Created by 冰封承諾Andy on 2019/7/22.
 */
public enum LoginResponseType {
    /**
     * 返回 JSON 形式的认证信息
     */
    JSON,
    /**
     * 默认重定向回原始界面
     */
    REDIRECT
}
