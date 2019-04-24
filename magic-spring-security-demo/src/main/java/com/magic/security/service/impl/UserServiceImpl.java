package com.magic.security.service.impl;

import com.magic.security.dto.response.User;
import com.magic.security.service.HelloService;
import com.magic.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HelloService helloService;

    @Override
    public User findById(String userId) {
        helloService.sayHello(userId);
        if("100".equals(userId)){
            User user = new User();
            user.setUserId("100");
            user.setUserName("admin");
            user.setPassword("admin123");
            user.setBirthday(new Date());
            return user;
        }
        return null;
    }

    @Override
    public String save(String openId) {
        return UUID.randomUUID().toString();
    }
}
