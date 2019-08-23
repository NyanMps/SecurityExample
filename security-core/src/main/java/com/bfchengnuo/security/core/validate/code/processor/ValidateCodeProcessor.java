package com.bfchengnuo.security.core.validate.code.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器通用接口
 * 可封装不同的处理器
 *
 * @author 冰封承諾Andy
 * @date 2019-08-19
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入 session 时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     *
     * @param request 请求对象
     * @throws Exception 创建发生异常
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest 请求对象
     * @throws Exception 校验发生异常
     */
    void validate(ServletWebRequest servletWebRequest);

}