package com.magic.security.demo.service.impl;

import com.magic.security.demo.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String message) {
        System.out.println(message);
    }
}
