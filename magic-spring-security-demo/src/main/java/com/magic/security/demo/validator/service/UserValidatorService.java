package com.magic.security.demo.validator.service;


import com.magic.security.demo.dto.response.User;
import com.magic.security.demo.service.UserService;
import com.magic.security.demo.validator.annotacion.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidatorService implements ConstraintValidator<UserValidator, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        User user = userService.findById(value);
        if (user == null) {
            return false;
        }
        return true;
    }
}
