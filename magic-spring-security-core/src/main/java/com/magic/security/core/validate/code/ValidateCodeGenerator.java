package com.magic.security.core.validate.code;

import com.magic.security.core.validate.code.sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
    public ValidateCode generate(ServletWebRequest request);
}
