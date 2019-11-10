package com.bfchengnuo.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存储逻辑
 *
 * @author 冰封承諾Andy
 * @date 2019-11-10
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);
}
