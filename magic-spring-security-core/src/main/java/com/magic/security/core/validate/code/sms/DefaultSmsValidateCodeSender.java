package com.magic.security.core.validate.code.sms;

public class DefaultSmsValidateCodeSender implements SmsValidateCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号"+mobile+"发送短信验证码"+code);
    }
}
