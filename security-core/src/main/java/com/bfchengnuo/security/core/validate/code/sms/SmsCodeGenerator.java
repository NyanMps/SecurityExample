package com.bfchengnuo.security.core.validate.code.sms;

import com.bfchengnuo.security.core.properties.SecurityProperties;
import com.bfchengnuo.security.core.validate.code.ValidateCode;
import com.bfchengnuo.security.core.validate.code.ValidateCodeGenerator;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码默认生成
 *
 * @see com.bfchengnuo.security.core.validate.code.image.ImageCodeGenerator 同类实现
 * @author 冰封承諾Andy
 * @date 2019-08-22
 */
@Component("smsValidateCodeGenerator")
@AllArgsConstructor
public class SmsCodeGenerator implements ValidateCodeGenerator {
    private final SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getValidateCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getValidateCode().getSms().getExpireIn());
    }
}
