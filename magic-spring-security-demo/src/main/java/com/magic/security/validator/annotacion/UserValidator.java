package com.magic.security.validator.annotacion;

import com.magic.security.validator.service.UserValidatorService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UserValidatorService.class)
public @interface UserValidator {

    boolean required() default true;

    String message() default "用户不存在！";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
