package com.magic.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class SpringSecurityDemoApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication2.class, args);
    }
}
