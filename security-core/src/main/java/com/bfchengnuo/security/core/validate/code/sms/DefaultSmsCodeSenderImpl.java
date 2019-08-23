package com.bfchengnuo.security.core.validate.code.sms;

/**
 * @author Created by 冰封承諾Andy on 2019/8/2.
 */
public class DefaultSmsCodeSenderImpl implements SmsCodeSender {
    @Override
    public void send(String phone, String code) {
        System.out.println("向手机" + phone + "发送短信验证码" + code);
    }
}
