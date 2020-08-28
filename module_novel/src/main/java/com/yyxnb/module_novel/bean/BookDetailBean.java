package com.yyxnb.module_novel.bean;

public class BookDetailBean  {

    // 名称
    public String name;
    // 内容
    public String content;
    // 注释
    public String commentary;
    // 翻译
    public String translation;
    // 赏析
    public String appreciation;
    // 解读
    public String interpretation;

    @Override
    public String toString() {
        return "BookDetailBean{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", commentary='" + commentary + '\'' +
                ", translation='" + translation + '\'' +
                ", appreciation='" + appreciation + '\'' +
                ", interpretation='" + interpretation + '\'' +
                '}';
    }
}
