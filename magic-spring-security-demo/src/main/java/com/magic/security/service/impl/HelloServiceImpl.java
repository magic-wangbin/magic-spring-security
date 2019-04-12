package com.magic.security.service.impl;

import com.magic.security.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String message) {
        System.out.println(message);
    }
}
