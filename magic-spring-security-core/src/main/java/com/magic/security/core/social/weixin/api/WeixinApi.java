/**
 *
 */
package com.magic.security.core.social.weixin.api;

/**
 * 微信API调用接口
 *
 * @author zhailiang
 *
 */
public interface WeixinApi {

    /**
     * 获取微信用户信息.
     * @param openId
     * @return
     */
    WeixinUserInfo getUserInfo(String openId);

}
