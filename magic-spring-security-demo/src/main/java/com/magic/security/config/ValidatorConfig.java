package com.magic.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
public class ValidatorConfig {
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        return bean;
    }

    @Bean
    public ParameterNameDiscoverer localVariableTableParameterNameDiscoverer(){
        return new LocalVariableTableParameterNameDiscoverer();
    }
}
