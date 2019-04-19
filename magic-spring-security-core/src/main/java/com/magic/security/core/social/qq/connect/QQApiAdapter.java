package com.magic.security.core.social.qq.connect;

import com.magic.security.core.social.qq.api.QQApi;
import com.magic.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ api 适配器.
 */
public class QQApiAdapter implements ApiAdapter<QQApi> {
    /**
     * 测试服务是否可用.
     * @param qqApi
     * @return
     */
    @Override
    public boolean test(QQApi qqApi) {
        return true;
    }

    /**
     * 设置数据库数据项.
     * @param qqApi
     * @param connectionValues
     */
    @Override
    public void setConnectionValues(QQApi qqApi, ConnectionValues connectionValues) {
        QQUserInfo qqUserInfo = qqApi.getUserInfo();
        //服务提供商提供的用户的唯一标识
        connectionValues.setProviderUserId(qqUserInfo.getOpenId());
        connectionValues.setDisplayName(qqUserInfo.getNickname());
        connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(QQApi qqApi) {
        return null;
    }

    @Override
    public void updateStatus(QQApi qqApi, String s) {

    }
}
