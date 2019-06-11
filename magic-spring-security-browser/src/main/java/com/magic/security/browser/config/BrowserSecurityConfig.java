package com.magic.security.browser.config;

import com.magic.security.core.authentication.AbstractChannelSecurityConfig;
import com.magic.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.magic.security.core.authorize.AuthorizeConfigManager;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
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
    private SpringSocialConfigurer customSocialConfigurer;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用户名密码认证流程
        applyPasswordAuthenticationConfig(http);
        //
        http
            //验证码
            .apply(validateCodeSecurityConfig)//
            .and()
            //手机
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            //第三方
            .apply(customSocialConfigurer)
            .and()
            //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
            .rememberMe()
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
            .userDetailsService(myUserDetialsService)
            .and()
            //session管理
            .sessionManagement()
            .invalidSessionStrategy(invalidSessionStrategy)//session无效策略
            .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//同一个用户在系统中的最大session数
            .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//达到最大session时是否阻止新的登录请求
            .expiredSessionStrategy(sessionInformationExpiredStrategy)//session过期策略
            .and()
            .and()
            //退出登录
            .logout()
            .logoutUrl(SecurityConstants.LOGIN_OUT_URL)//退出登录URL
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies(SecurityConstants.JSESSION_ID)
            .and()
            //csrf禁用
            .csrf().disable();
        // 授权请求
        authorizeConfigManager.config(http.authorizeRequests());

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
