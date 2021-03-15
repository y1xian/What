package com.yyxnb.module_server.bean.response;

public class LoginVo {

    private String token;
    private UserVo user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }
}
