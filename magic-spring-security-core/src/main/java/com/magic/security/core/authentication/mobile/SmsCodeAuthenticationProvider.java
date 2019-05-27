package com.magic.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 手机号验证.
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取手机号码
        String mobile = String.valueOf(authentication.getPrincipal());
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);

        // not found throw exception
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("未查询到手机号为[" + mobile + "]的用户！");
        }

        //查询到之后继续封装token
        SmsCodeAuthenticationToken smsCodeAuthResult = new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        //封装其他信息如request信息
        smsCodeAuthResult.setDetails(authentication.getDetails());

        return smsCodeAuthResult;
    }

    /**
     * 传入的认证方式是否是手机号认证（根据token的类型进行判断）.
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //authentication 是否是SmsCodeAuthenticationToken，或者是SmsCodeAuthenticationToken的实现
        // 也就是authentication可以转化成SmsCodeAuthenticationToken
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
