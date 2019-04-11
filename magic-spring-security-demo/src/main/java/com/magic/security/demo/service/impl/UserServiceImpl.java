package com.magic.security.demo.service.impl;

import com.magic.security.demo.dto.response.User;
import com.magic.security.demo.service.HelloService;
import com.magic.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HelloService helloService;

    @Override
    public User findById(String userId) {
        System.out.println("begin");
        helloService.sayHello(userId);
        System.out.println("start");
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
}
