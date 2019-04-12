package com.magic.security.service;

import com.magic.security.dto.response.User;

public interface UserService {
    public User findById(String userId);
}
