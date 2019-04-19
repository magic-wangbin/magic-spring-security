package com.magic.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 获取用户信息api(非单例).
 */
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQApi {

    private static final String GET_USERINFO_URL = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private static final String GET_OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String appId;

    private final String openId;

    public QQApiImpl(String accessToken, String appId) {

        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(GET_OPENID_URL, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        this.openId = StringUtils.substringBetween(result, "openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        //
        String url = String.format(GET_USERINFO_URL, appId, openId);
        String qqUserInfoResponse = getRestTemplate().getForObject(url, String.class);

        try {

            QQUserInfo qqUserInfo = objectMapper.readValue(qqUserInfoResponse, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);

            return qqUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
