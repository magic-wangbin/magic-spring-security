package com.magic.security.app.config;

import com.magic.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 使用redis存储token的配置，只有在magic.security.oauth2.tokenStore配置为redis时生效
     *
     * @author zhailiang
     */
    @Bean
    @ConditionalOnProperty(prefix = "magic.security.oauth2", name = "tokenStore", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);
    }


    /**
     * 使用Jwt进行Token的配置.
     */
    @Configuration
    @ConditionalOnProperty(prefix = "magic.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = false)
    public static class JwtConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getSignKey());
            return jwtAccessTokenConverter;
        }

        /**
         * 用于扩展JWT
         *
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new MagicTokenJwtEnhancer();
        }

    }
}
