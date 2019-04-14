package com.magic.security.core.validate.code;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码校验的拦截器.
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //只有登录的请求才需要校验并且为post
        if (StringUtils.equals("/authentication/form", request.getRequestURI())
                && StringUtils.endsWithIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 验证码的校验
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //从session中取值
        ImageCode imageCodeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        ;
        //从request中取值
        String imageCode = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(imageCode)) {
            throw new ValidateCodeException("图形验证码的值不能为空！");
        }

        if (imageCodeInSession == null) {
            throw new ValidateCodeException("验证码不存在！");
        }

        if (imageCodeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已经过期！");
        }

        if (!StringUtils.endsWithIgnoreCase(imageCodeInSession.getCode(), imageCode)) {
            throw new ValidateCodeException("验证码不匹配！");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);

    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
