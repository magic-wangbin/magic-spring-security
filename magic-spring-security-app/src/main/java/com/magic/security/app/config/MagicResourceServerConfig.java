package com.magic.security.app.config;

import com.magic.security.app.authentication.openId.OpenIdAuthenticationSecurityConfig;
import com.magic.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer customSocialConfigurer;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        //用户名密码认证流程
//        http.formLogin()
//            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
//            .successHandler(magicAuthenticationSuccessHandler)
//            .failureHandler(magicAuthenticationFailureHandler);
//
//        //
//        http
//
//            //验证码拦截器
//            .apply(validateCodeSecurityConfig)//
//            .and()
//
//            //手机号认证流程
//            .apply(smsCodeAuthenticationSecurityConfig)
//            .and()
//
//            //第三方处理
//            .apply(customSocialConfigurer)
//            .and()
//
//            //openId登录拦截
//            .apply(openIdAuthenticationSecurityConfig)
//            .and()
//
//            //非拦截的请求
//            .authorizeRequests()
//            .antMatchers(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
//                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                securityProperties.getBrowser().getLoginPage(),
//                securityProperties.getBrowser().getSignUpUrl(),
//                SecurityConstants.DEFAULT_FAVICON_ICO,
//
//                //其他第三方的配置 TODO
//                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
//                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
//                //退出登录页
//                securityProperties.getBrowser().getLogOutUrl(),
//
//                "/user/regist", SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL
//            )
//            .permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//
//            //csrf禁用
//            .csrf().disable();

        http.formLogin()
            .successHandler(magicAuthenticationSuccessHandler)//登录成功处理器
            .and()
            .authorizeRequests().anyRequest().authenticated().and()
            .csrf().disable();

    }
}
