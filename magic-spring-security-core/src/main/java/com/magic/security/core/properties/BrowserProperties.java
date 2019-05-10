package com.magic.security.core.properties;

import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.properties.enums.LoginType;

public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String signUpUrl = "/imooc-signUp.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;

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

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
