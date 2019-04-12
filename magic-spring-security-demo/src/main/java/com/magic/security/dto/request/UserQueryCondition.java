package com.magic.security.dto.request;

import java.io.Serializable;

public class UserQueryCondition implements Serializable {
    private static final long serialVersionUID = -8677610346663911127L;

    private String userName;

    private Integer age;

    private Integer ageTo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }
}
