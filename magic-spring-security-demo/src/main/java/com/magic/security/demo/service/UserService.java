package com.magic.security.demo.service;

import com.magic.security.demo.dto.response.User;

public interface UserService {
    public User findById(String userId);
}
