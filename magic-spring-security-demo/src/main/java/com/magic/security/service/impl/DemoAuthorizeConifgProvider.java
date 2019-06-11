package com.magic.security.service.impl;

import com.magic.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class DemoAuthorizeConifgProvider implements AuthorizeConfigProvider {
    /**
     * @param config
     * @return <p>返回的boolean表示配置中是否有针对anyRequest的配置。</p>
     * <p>1:在整个授权配置中，应该有且仅有一个针对anyRequest的配置。如果有多个针对anyRequest的配置，则会抛出异常。</p>
     * <p>2:如果所有的实现都没有针对anyRequest的配置，系统会自动增加一个anyRequest().authenticated()的配置。</p>
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user/me").hasRole("ADMIN2");//仅仅是一个测试.
        return false;
    }
}
