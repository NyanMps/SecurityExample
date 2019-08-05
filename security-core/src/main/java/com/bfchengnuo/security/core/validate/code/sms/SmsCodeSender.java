package com.bfchengnuo.security.core.validate.code.sms;

/**
 * 发送短信验证码
 *
 * @author Created by 冰封承諾Andy on 2019/8/2.
 */
public interface SmsCodeSender {
    void send(String phone, String code);
}
