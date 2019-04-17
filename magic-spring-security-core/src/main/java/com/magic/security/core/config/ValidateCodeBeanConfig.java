package com.magic.security.core.config;

import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.validate.code.*;
import com.magic.security.core.validate.code.image.ImageValidateCodeGenerator;
import com.magic.security.core.validate.code.sms.DefaultSmsValidateCodeSender;
import com.magic.security.core.validate.code.sms.SmsValidateCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageValidateCodeGenerator imageValidateCodeGenerator = new ImageValidateCodeGenerator();
        imageValidateCodeGenerator.setSecurityProperties(securityProperties);
        return imageValidateCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(value = SmsValidateCodeSender.class)
    public SmsValidateCodeSender smsCodeSender(){
        return new DefaultSmsValidateCodeSender();
    }
}
