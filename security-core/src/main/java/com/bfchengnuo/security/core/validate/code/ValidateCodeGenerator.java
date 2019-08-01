package com.bfchengnuo.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 *
 * @author Created by 冰封承諾Andy on 2019/8/1.
 */
public interface ValidateCodeGenerator {
    /**
     * 验证码实现方法接口
     */
    ImageCode generate(ServletWebRequest request);

 }