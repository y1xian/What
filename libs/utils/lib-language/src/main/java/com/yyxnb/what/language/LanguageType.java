package com.yyxnb.what.language;

public enum LanguageType {
    // 系统跟随
    LANGUAGE_system("system"),
    // 中文
    LANGUAGE_zh("zh"),
    // 繁体
    LANGUAGE_zh_tw("zh-rTW"),
    //英语
    LANGUAGE_en("en");

    //自定义属性
    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
