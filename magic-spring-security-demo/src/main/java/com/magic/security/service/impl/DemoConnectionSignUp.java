package com.magic.security.service.impl;

import com.magic.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    /* (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionSignUp#execute(org.springframework.social.connect.Connection)
     */
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        String userId = userService.save(connection.createData().getProviderId());
        return userId;
    }

}