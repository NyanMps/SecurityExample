package com.bfchengnuo.security.core.social.qq.connect;

import com.bfchengnuo.security.core.social.qq.api.QQ;
import com.bfchengnuo.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 用于 {@link QQConnectionFactory} 的 api 适配器
 * 负责将个性化的服务提供商给的个性化数据格式转换为 social 通用的格式
 * 范型中指的是要适配的 api 的实现类的类型，就是 QQ 了
 *
 * @author 冰封承諾Andy
 * @date 2019-09-07
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        // 判断服务是否可用，我们暂时认为一直可用
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        // 必要数据的转换与填充
        QQUserInfo userInfo = qq.getUserInfo4QQ();

        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        // 设置个人主页
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
