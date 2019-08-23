package com.bfchengnuo.security.core.validate.code.sms;

import com.bfchengnuo.security.core.properties.SecurityConstants;
import com.bfchengnuo.security.core.validate.code.ValidateCode;
import com.bfchengnuo.security.core.validate.code.processor.AbstractValidateCodeProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器实现
 *
 * @see com.bfchengnuo.security.core.validate.code.image.ImageCodeProcessor 同类实现
 * @author 冰封承諾Andy
 * @date 2019-08-22
 */
@Component("smsValidateCodeProcessor")
@AllArgsConstructor
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    /**
     * 短信验证码发送器
     */
    private final SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
