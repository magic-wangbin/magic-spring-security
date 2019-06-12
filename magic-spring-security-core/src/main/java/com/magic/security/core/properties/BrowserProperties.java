package com.magic.security.core.properties;

import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.properties.enums.SignInResponseType;

public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String signUpUrl = SecurityConstants.DEFAULT_SIGN_UP_PAGE;

    //退出登录页面[默认为空，返回json]
    private String logOutUrl;

    private SignInResponseType signInResponseType = SignInResponseType.JSON;

    private int rememberMeSeconds = 3600;

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     * <p>
     * 只在signInResponseType为REDIRECT时生效
     */
    private String singInSuccessUrl;

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getLogOutUrl() {
        return logOutUrl;
    }

    public void setLogOutUrl(String logOutUrl) {
        this.logOutUrl = logOutUrl;
    }

    public SignInResponseType getSignInResponseType() {
        return signInResponseType;
    }

    public void setSignInResponseType(SignInResponseType signInResponseType) {
        this.signInResponseType = signInResponseType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSingInSuccessUrl() {
        return singInSuccessUrl;
    }

    public void setSingInSuccessUrl(String singInSuccessUrl) {
        this.singInSuccessUrl = singInSuccessUrl;
    }
}
