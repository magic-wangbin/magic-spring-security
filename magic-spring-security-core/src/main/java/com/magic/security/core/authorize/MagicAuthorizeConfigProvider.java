package com.magic.security.core.authorize;

import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Order 设置最小值，最先加载.
 */
@Component
@Order(Integer.MIN_VALUE)
public class MagicAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param config
     * @return <p>返回的boolean表示配置中是否有针对anyRequest的配置。</p>
     * <p>1:在整个授权配置中，应该有且仅有一个针对anyRequest的配置。如果有多个针对anyRequest的配置，则会抛出异常。</p>
     * <p>2:如果所有的实现都没有针对anyRequest的配置，系统会自动增加一个anyRequest().authenticated()的配置。</p>
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 公共的不需要认证的URL
        config.antMatchers(
            // 当请求需要身份认证时，默认跳转的url
            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
            // 默认的手机验证码登录请求处理url
            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
            // 默认的OPENID登录请求处理url
            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,
            // 默认的处理验证码的url前缀
            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
            // 登录
            securityProperties.getBrowser().getLoginPage(),
            // 注册
            securityProperties.getBrowser().getSignUpUrl(),
            // session失效的url
            securityProperties.getBrowser().getSession().getSessionInvalidUrl())
            .permitAll();

        //退出登录页的配置
        if (StringUtils.isNotBlank(securityProperties.getBrowser().getLogOutUrl())) {
            config.antMatchers(securityProperties.getBrowser().getLogOutUrl()).permitAll();
        }

        return false;
    }
}
