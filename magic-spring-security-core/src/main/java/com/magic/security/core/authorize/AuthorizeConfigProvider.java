package com.magic.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器，各个模块和业务系统可以通过实现此接口向系统添加自定义授权配置。
 */
public interface AuthorizeConfigProvider {
    /**
     * @param config
     * @return <p>返回的boolean表示配置中是否有针对anyRequest的配置。</p>
     * <p>1:在整个授权配置中，应该有且仅有一个针对anyRequest的配置。如果有多个针对anyRequest的配置，则会抛出异常。</p>
     * <p>2:如果所有的实现都没有针对anyRequest的配置，系统会自动增加一个anyRequest().authenticated()的配置。</p>
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
