package com.bfchengnuo.security.core.social.wx.connect;

import com.bfchengnuo.security.core.social.wx.api.Weixin;
import com.bfchengnuo.security.core.social.wx.api.WeixinUserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api 适配器，将微信 api 的数据模型转为 spring social 的标准模型。
 *
 * @author 冰封承諾Andy
 * @date 2019-09-19
 */
@AllArgsConstructor
@NoArgsConstructor
public class WeixinAdapter implements ApiAdapter<Weixin> {
    private String openId;

    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {
        //do nothing
    }
}
