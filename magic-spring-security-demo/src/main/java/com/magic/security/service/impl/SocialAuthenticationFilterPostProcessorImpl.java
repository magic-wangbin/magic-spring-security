package com.magic.security.service.impl;//package com.magic.security.app.social.impl;

import com.magic.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 * app环境下使用
 */
//@Component
public class SocialAuthenticationFilterPostProcessorImpl implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    protected AuthenticationSuccessHandler magicAuthenticationSuccessHandler;

    /**
     * @param socialAuthenticationFilter
     */
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(magicAuthenticationSuccessHandler);
    }
}
