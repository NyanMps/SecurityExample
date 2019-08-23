package com.bfchengnuo.security.core.validate.code.image;

import com.bfchengnuo.security.core.validate.code.processor.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 * 名字有规范，必须为 imageValidateCodeProcessor 格式；
 * 具体原因参考 {@link AbstractValidateCodeProcessor} 实现
 *
 * @see com.bfchengnuo.security.core.validate.code.sms.SmsCodeProcessor 同类实现
 * @author 冰封承諾Andy
 * @date 2019-08-22
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        // 将图片写出到响应中
        assert request.getResponse() != null;
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
