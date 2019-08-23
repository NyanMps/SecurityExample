package com.bfchengnuo.security.core.validate.code;


import com.bfchengnuo.security.core.properties.SecurityConstants;

/**
 * 关于验证码类型的枚举定义
 *
 * @author 冰封承諾Andy
 * @date 2019-08-19
 */
public enum ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 构造方法。
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();
}
