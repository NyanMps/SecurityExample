package com.bfchengnuo.security.core.properties;

import lombok.Data;

/**
 * 验证码配置
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
@Data
public class ValidateCodeProperties {
    private ImageCodeProperties imageCode = new ImageCodeProperties();
}
