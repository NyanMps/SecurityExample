package com.bfchengnuo.security.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 实现社交登陆后自动注册
 *
 * @author 冰封承諾Andy
 * @date 2019-09-17
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }
}
