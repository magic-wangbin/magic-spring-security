package com.magic.security.browser.config;

import com.magic.security.core.authentication.AbstractChannelSecurityConfig;
import com.magic.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetialsService;


    //-------------------------------------------------------

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer magicSocialSecurityConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用户名密码认证流程
        applyPasswordAuthenticationConfig(http);
        ////验证码拦截器
        http.apply(validateCodeSecurityConfig)//
            .and()
            //手机号认证流程
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()

            .apply(magicSocialSecurityConfig)
            .and()

            //记住我功能
            .rememberMe()
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
            .userDetailsService(myUserDetialsService)
            .and()

            //非拦截的请求
            .authorizeRequests()
            .antMatchers(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_FAVICON_ICO)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()

            //csrf禁用
            .csrf().disable();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
