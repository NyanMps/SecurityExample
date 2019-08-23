package com.bfchengnuo.security.core.validate.code;

import com.bfchengnuo.security.core.properties.SecurityConstants;
import com.bfchengnuo.security.core.validate.code.processor.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 负责验证码相关的请求处理
 * 用于获取 短信、图形 校验码
 *
 * 使用的 {@link ServletWebRequest} 是 Spring 的包装，不仅仅可以包装 Request
 *
 * @author Created by 冰封承諾Andy on 2019/7/25.
 */
@RestController
public class ValidateCodeController {
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，
     * 根据验证码类型不同，通过 validateCodeProcessorHolder 来调用不同的 {@link com.bfchengnuo.security.core.validate.code.processor.ValidateCodeProcessor} 接口实现
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }
}
