package com.magic.security.core.validate.code.sms;

public interface SmsValidateCodeSender {
    void send(String mobile,String code);
}
