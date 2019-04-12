package com.magic.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringSecurityDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }
}
