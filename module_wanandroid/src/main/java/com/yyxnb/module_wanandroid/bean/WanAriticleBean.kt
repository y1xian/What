package com.yyxnb.module_wanandroid.bean;

import java.util.List;

public class WanAriticleBean {


    /**
     * apkLink :
     * audit : 1
     * author :
     * canEdit : false
     * chapterId : 502
     * chapterName : 自助
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 14091
     * link : https://juejin.im/post/5ef9327e6fb9a07e716acac4
     * niceDate : 2020-06-30 08:24
     * niceShareDate : 2020-06-30 08:24
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1593476689000
     * realSuperChapterId : 493
     * selfVisible : 0
     * shareDate : 1593476689000
     * shareUser : JsonChao
     * superChapterId : 494
     * superChapterName : 广场Tab
     * tags : []
     * title : 深入探索 Gradle 自动化构建技术（九、Gradle 插件平台化框架 ByteX 探秘之旅）
     * type : 0
     * userId : 611
     * visible : 1
     * zan : 0
     */

    public String apkLink;
    public int audit;
    public String author;
    public boolean canEdit;
    public int chapterId;
    public String chapterName;
    public boolean collect;
    public int courseId;
    public String desc;
    public String descMd;
    public String envelopePic;
    public boolean fresh;
    public int id;
    public String link;
    public String niceDate;
    public String niceShareDate;
    public String origin;
    public String prefix;
    public String projectLink;
    public long publishTime;
    public int realSuperChapterId;
    public int selfVisible;
    public long shareDate;
    public String shareUser;
    public int superChapterId;
    public String superChapterName;
    public String title;
    public int type;
    public int userId;
    public int visible;
    public int zan;
    public List<DataBeans> tags;

    // 轮播的
    public String imagePath;
    public String url;
    public int isVisible;
    public int order;

    public static class DataBeans{
        public String name;
        public String url;
    }

}
