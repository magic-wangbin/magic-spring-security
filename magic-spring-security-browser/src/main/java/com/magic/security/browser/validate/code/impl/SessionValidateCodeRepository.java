package com.magic.security.browser.validate.code.impl;

import com.magic.security.core.properties.enums.ValidateCodeType;
import com.magic.security.core.validate.code.ValidateCodeRepository;
import com.magic.security.core.validate.code.sms.ValidateCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    public static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 获取session对象.
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(validateCodeType), new ValidateCode(code.getCode(), code.getExpireTime()));
    }

    /**
     * 移除验证码
     *
     * @param request
     * @param codeType
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(codeType));
    }

    /**
     * 获取验证码.
     *
     * @param request
     * @param codeType
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(codeType));
    }

    /**
     * 构建验证码放入session时的key
     *
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
