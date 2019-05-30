//package com.magic.security.app.social;
//
//import com.magic.security.core.properties.constans.SecurityConstants;
//import com.magic.security.core.social.support.CustomSocialConfigurer;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//
//
//public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
//
//    /* (non-Javadoc)
//     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
//     */
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    /* (non-Javadoc)
//     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
//     */
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (StringUtils.equals(beanName, "customSocialConfigurer")) {
//            CustomSocialConfigurer config = (CustomSocialConfigurer) bean;
//            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
//            return config;
//        }
//        return bean;
//    }
//}
