package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * 图形验证码配置
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Data
public class ImageCodeProperties {
    /**
     * 图形验证码的宽
     */
    private int width = 67;
    /**
     * 图形验证码的高
     */
    private int height = 23;
    /**
     * 图形验证码的长度
     */
    private int length = 4;
    /**
     * 失效时间(秒)
     */
    private int expireIn = 60;
    /**
     * 需要验证码的url
     */
    private String url;
}
