package com.magic.security.core.properties.enums;

import com.magic.security.core.properties.constans.SecurityConstants;

public enum ValidateCodeType {

    //短信验证码
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }

        @Override
        public String getParamNameDesOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_DESC_SMS;
        }

    },
    //图形验证码
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }

        @Override
        public String getParamNameDesOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_DESC_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字（根据请求中的参数判定是短信验证码还是图形验证码）.
     *
     * @return
     */
    public abstract String getParamNameOnValidate();

    public abstract String getParamNameDesOnValidate();

}
