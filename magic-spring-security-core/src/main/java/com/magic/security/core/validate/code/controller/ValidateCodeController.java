package com.magic.security.core.validate.code.controller;

import com.magic.security.core.validate.code.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 生成验证码.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletRequestBindingException
     */
    @GetMapping("/code/{type}")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("type") String type) throws Exception {
        validateCodeProcessors.get(type.concat("CodeProcessor")).create(new ServletWebRequest(request,response));
    }

}

