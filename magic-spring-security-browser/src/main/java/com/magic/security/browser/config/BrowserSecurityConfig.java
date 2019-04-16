package com.magic.security.browser.config;

import com.magic.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.validate.code.ValidateCodeFilter;
import com.magic.security.core.validate.code.ValidateSmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler magicAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler magicAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetialsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(magicAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        ValidateSmsCodeFilter smsCodeFilter = new ValidateSmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(magicAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

//        http.httpBasic()
        http
                //验证码filter
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)

                //表单登录
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(magicAuthenticationSuccessHandler)
                .failureHandler(magicAuthenticationFailureHandler)
                .and()

                //记住我
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(myUserDetialsService)
                .and()

                //请求认证
                .authorizeRequests()
                .antMatchers(
                        "/code/*",
                        securityProperties.getBrowser().getLoginPage(),
                        "/authentication/require",
                        "/static/favicon.ico"

                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()

                //csrf禁用
                .csrf().disable()

                //添加手机号验证流程
                .apply(smsCodeAuthenticationSecurityConfig)
        ;
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
