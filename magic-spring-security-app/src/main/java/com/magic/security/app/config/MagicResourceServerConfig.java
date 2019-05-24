package com.magic.security.app.config;

import com.magic.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务.
 */
@Configuration
@EnableResourceServer
public class MagicResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler magicAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler magicAuthenticationFailureHandler;

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        //用户名密码认证流程
//        http.formLogin()
//            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
//            .successHandler(magicAuthenticationSuccessHandler)
//            .failureHandler(magicAuthenticationFailureHandler);
//    }
}
