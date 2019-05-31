package com.magic.security.app.config;

import com.magic.security.core.properties.OAuth2ClientProperties;
import com.magic.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务.
 */
@Configuration
@EnableAuthorizationServer
public class MagicAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        throws Exception {
        endpoints
            .tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }

    /**
     * 客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId())
                    .secret(passwordEncoder.encode(client.getClientSecret()))
                    //.redirectUris("http://example.com")
                    .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                    .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                    .refreshTokenValiditySeconds(2592000)
                    .scopes("all");
            }
        }


    }

}
