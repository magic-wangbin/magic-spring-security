package com.magic.security.app.social;

import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.social.support.CustomSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * app如果沒有用戶信息，也就是沒有登陸的情況下，將調用注冊信息接口.
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "customSocialConfigurer")) {
            CustomSocialConfigurer config = (CustomSocialConfigurer) bean;
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return config;
        }
        return bean;
    }
}
