package com.yyxnb.lib_audio.bean;


import java.io.Serializable;

/**
 * 歌曲实体
 */
public class AudioBean implements Serializable {

    public String id;
    //地址
    public String url;

    //歌名
    public String name;

    //作者
    public String author;

    //所属专辑
    public String album;

    //信息
    public String albumInfo;

    //专辑封面
    public String albumPic;

    //时长
    public String totalTime;

    public AudioBean(String id, String url, String name, String author, String album, String albumPic) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.author = author;
        this.album = album;
        this.albumPic = albumPic;
    }

    public AudioBean(String url, String name, String author, String albumPic) {
        this.url = url;
        this.name = name;
        this.author = author;
        this.albumPic = albumPic;
    }

    public AudioBean(String id, String url, String name, String author, String album, String albumInfo, String albumPic, String totalTime) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.author = author;
        this.album = album;
        this.albumInfo = albumInfo;
        this.albumPic = albumPic;
        this.totalTime = totalTime;
    }
}
