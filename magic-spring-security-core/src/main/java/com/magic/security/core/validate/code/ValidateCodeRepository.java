package com.magic.security.core.validate.code;

import com.magic.security.core.properties.enums.ValidateCodeType;
import com.magic.security.core.validate.code.sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码持久化处理器.
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);

    /**
     * 获取验证码.
     *
     * @param request
     * @param codeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType codeType);

}
