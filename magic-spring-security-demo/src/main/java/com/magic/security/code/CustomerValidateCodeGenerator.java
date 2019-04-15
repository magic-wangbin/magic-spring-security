package com.magic.security.code;

import com.magic.security.core.validate.code.ImageCode;
import com.magic.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;
//@Component(value = "imageCodeGenerator")
public class CustomerValidateCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generator(ServletWebRequest request) {
        System.out.println("这是自定义的图形验证码规则，覆盖默认！");
        return null;
    }
}
