package com.magic.security.core.config;

import com.magic.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SecurityPropertiesConfig {
}
