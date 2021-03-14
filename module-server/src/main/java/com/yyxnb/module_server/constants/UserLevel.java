package com.yyxnb.module_server.constants;

public enum UserLevel {

    /**
     * 游客
     */
    VISITOR("0"),
    /**
     * 一般用户
     */
    GENERAL("1"),
    /**
     * 会员
     */
    VIP("2"),
    /**
     * 超级会员
     */
    SVIP("3"),
    /**
     * 黑名单
     */
    BLACKLIST("5"),
    /**
     * 管理
     */
    ADMIN("6");


    private String level;

    UserLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
