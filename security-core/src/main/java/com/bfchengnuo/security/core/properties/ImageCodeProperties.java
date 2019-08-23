package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * 图形验证码配置
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 67;
    private int height = 23;
}
