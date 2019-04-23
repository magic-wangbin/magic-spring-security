package com.magic.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class QQOAuth2Template extends OAuth2Template {

    private static final Logger LOGGER = LoggerFactory.getLogger(QQOAuth2Template.class);

    /**
     * 初始化时setUseParametersForClientAuthentication
     *
     * @param clientId
     * @param clientSecret
     * @param authorizeUrl
     * @param accessTokenUrl
     */
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    //使用内置的OAuth2Template请求不支持string类型的返回结果，只支持以下三种
    //converters.add(new FormHttpMessageConverter());
    //onverters.add(new FormMapHttpMessageConverter());
    //converters.add(new MappingJackson2HttpMessageConverter());
    // 所以需要重写OAuth2Template中的createRestTemplate方法，添加返回结果处理类型
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * 处理授权返回结果.
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    public AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        //
        String response = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        LOGGER.info("获取accessToke的响应：{}", response);

        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");

        String accessToken = StringUtils.substringAfterLast(strings[0], "=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(strings[1], "="));
        String refreshToken = StringUtils.substringAfterLast(strings[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

}
