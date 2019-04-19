package com.magic.security.core.social.qq.connect;

import com.magic.security.core.social.qq.api.QQApi;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * qq数据库连接工厂.
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {
    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQApiAdapter());
    }
}
