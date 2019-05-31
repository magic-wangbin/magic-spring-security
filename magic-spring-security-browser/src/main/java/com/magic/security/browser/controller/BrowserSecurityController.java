package com.magic.security.browser.controller;

import com.magic.security.core.properties.SecurityProperties;
import com.magic.security.core.properties.constans.SecurityConstants;
import com.magic.security.core.social.controller.SocialController;
import com.magic.security.core.support.SimpleResponse;
import com.magic.security.core.support.SocialUserInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录跳转使用.
 */
@RestController
public class BrowserSecurityController extends SocialController {

    private Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;


    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 需要登录的请求跳转或提示.
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的url地址为：" + redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response,
                    securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请先进行登录!");
    }

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof SocialAuthenticationToken) {
            logger.info("第三方登录");
            String openId = ((SocialAuthenticationToken) authentication).getConnection().createData().getProviderUserId();
            logger.info("openId:" + openId);
        }


        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connection);
    }

}
