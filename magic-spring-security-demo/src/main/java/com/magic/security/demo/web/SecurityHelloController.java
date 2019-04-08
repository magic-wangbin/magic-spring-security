package com.magic.security.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityHelloController extends BaseController {
    @RequestMapping("hello")
    public String sayHello() {
        return "hello,spring security!";
    }
}
