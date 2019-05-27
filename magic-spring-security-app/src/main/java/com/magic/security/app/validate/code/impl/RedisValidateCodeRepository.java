package com.magic.security.app.validate.code.impl;

import com.magic.security.core.properties.enums.ValidateCodeType;
import com.magic.security.core.validate.code.ValidateCodeRepository;
import com.magic.security.core.validate.code.exception.ValidateCodeException;
import com.magic.security.core.validate.code.sms.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 保存验证码
     *
     * @param request
     * @param code
     * @param validateCodeType
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(request, validateCodeType), new ValidateCode(code.getCode(), code.getExpireTime()), 30, TimeUnit.MINUTES);
    }

    /**
     * 移除验证码
     *
     * @param request
     * @param codeType
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        redisTemplate.delete(buildKey(request, codeType));
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
        Object value = redisTemplate.opsForValue().get(buildKey(request, codeType));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    /**
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
