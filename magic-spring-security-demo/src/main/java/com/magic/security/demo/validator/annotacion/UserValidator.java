package com.magic.security.demo.validator.annotacion;

import com.magic.security.demo.validator.service.UserValidatorService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidatorService.class)
@Documented
public @interface UserValidator {

    boolean required() default true;

    String message() default "用户不存在！";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
