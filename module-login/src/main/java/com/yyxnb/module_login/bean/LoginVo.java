package com.yyxnb.module_login.bean;

public class LoginVo {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "token='" + token + '\'' +
                '}';
    }
}
