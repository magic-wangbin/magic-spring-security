package com.magic.security.social.qq.api;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";

    private static final String GET_TOCKEN_URL = "https://graph.qq.com/oauth2.0/token";

    private final String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new OAuth2Template(appId,appSecret,AUTHORIZE_URL,GET_TOCKEN_URL));
        this.appId = appId;
    }

    @Override
    public QQApi getApi(String accessToken) {
        return new QQApiImpl(accessToken,appId);
    }
}
