package com.magic.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    public ImageCode generator(ServletWebRequest request);
}
