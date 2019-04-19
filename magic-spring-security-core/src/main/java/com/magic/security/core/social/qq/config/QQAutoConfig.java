package com.magic.security.core.social.qq.config;

import com.magic.security.core.properties.QQProperties;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@ConditionalOnProperty(prefix = "magic.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties springProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(this.createConnectionFactory());
    }

    private ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = springProperties.getSocial().getQq();
        String providerId = qqProperties.getProviderId();
        String appId = qqProperties.getAppId();
        String appSecret = qqProperties.getAppSecret();
        return new QQConnectionFactory(providerId, appId, appSecret);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
