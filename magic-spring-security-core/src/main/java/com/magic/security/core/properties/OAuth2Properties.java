package com.magic.security.core.properties;

public class OAuth2Properties {
    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

    private String signKey = "magic";

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}
