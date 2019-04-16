package com.magic.security.core.validate.code;

import com.magic.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 手机验证码是否正确的校验.
 */
public class ValidateSmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urlSet = new HashSet<>();

    private SecurityProperties securityProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //初始化URL
        String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        if(urls!=null){
            for (String temp : urls) {
                urlSet.add(temp);
            }
        }
        urlSet.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean needFilter = false;
        for (String url : urlSet) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                needFilter = true;
            }
        }

        if (needFilter) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 验证码的校验
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //从session中取值
        ImageCode imageCodeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

        //从request中取值
        String imageCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

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

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}