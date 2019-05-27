package com.magic.security.core.validate.code.impl;

import com.magic.security.core.properties.enums.ValidateCodeType;
import com.magic.security.core.validate.code.ValidateCodeGenerator;
import com.magic.security.core.validate.code.ValidateCodeProcessor;
import com.magic.security.core.validate.code.ValidateCodeRepository;
import com.magic.security.core.validate.code.exception.ValidateCodeException;
import com.magic.security.core.validate.code.sms.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成
        T validateCode = generate(request);
        //添加到session
        save(request, validateCode);
        //发送
        send(request, validateCode);
    }

    private T generate(ServletWebRequest request) {
        return (T) (findValidateCodeGenerator().generate(request));

    }

    private ValidateCodeGenerator findValidateCodeGenerator() {
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return validateCodeGenerator;
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, validateCode, getValidateCodeType());
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), ValidateCodeProcessor.class.getSimpleName());
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 校验验证码.
     *
     * @param request
     */
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType();
//        String sessionKey = getSessionKey();

//        T codeInSession = (T) sessionStrategy.getAttribute(request, sessionKey);
        T codeInSession = (T) validateCodeRepository.get(request, processorType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType.getParamNameDesOnValidate() + "的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType.getParamNameDesOnValidate() + "不存在");
        }

        if (codeInSession.isExpried()) {
//            sessionStrategy.removeAttribute(request, sessionKey);
            validateCodeRepository.remove(request, getValidateCodeType());
            throw new ValidateCodeException(processorType.getParamNameDesOnValidate() + "已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType.getParamNameDesOnValidate() + "不匹配");
        }

        validateCodeRepository.remove(request, getValidateCodeType());
//        sessionStrategy.removeAttribute(request, sessionKey);
    }


}
