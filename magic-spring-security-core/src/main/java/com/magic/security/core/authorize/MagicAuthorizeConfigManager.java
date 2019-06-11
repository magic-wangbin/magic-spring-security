package com.magic.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 授权收集器.
 */
@Component
public class MagicAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    List<AuthorizeConfigProvider> authorizeConfigProviderList;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // config中是否存在AnyRequest
        boolean isAnyRequestConfig = false;
        // AnyRequest 配置的名称
        String isAnyRequestConfigName = null;

        for (AuthorizeConfigProvider provider : authorizeConfigProviderList) {
            boolean currentConfig = provider.config(config);
            String currentConfigName = provider.getClass().getSimpleName();
            //已经存在，重复
            if (isAnyRequestConfig && currentConfig) {
                throw new RuntimeException("重复的anyRequest配置"
                    + "[已存在:" + isAnyRequestConfigName + ",当前:" + currentConfigName + "]");
            }
            //第一次查询到
            else if (currentConfig) {
                isAnyRequestConfig = true;
                isAnyRequestConfigName = currentConfigName;
            }
        }
    }
}
