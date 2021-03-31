package com.yyxnb.module_music.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.yyxnb.what.music.interfaces.IMusic;

import java.io.Serializable;

/**
 * 歌曲实体
 */
@Entity(tableName = "music"/*, primaryKeys = {"mid"}*/)
public class MusicBean implements IMusic, Serializable {

    // 视频id
    @NonNull
    @PrimaryKey
    public String mid;

    // 地址
    public String url;

    // 歌名
    public String title;

    // 作者
    public String author;

    // 所属专辑
    public String album;

    // 信息
    public String albumInfo;

    // 封面
    public String albumPic;

    // 时长
    public String totalTime;

    // 类型
    public String type;

    public MusicBean() {
    }

    @Ignore
    public MusicBean(String mid, String url, String title, String author, String album, String albumPic) {
        this.mid = mid;
        this.url = url;
        this.title = title;
        this.author = author;
        this.album = album;
        this.albumPic = albumPic;
    }

    @Ignore
    public MusicBean(String url, String title, String author, String albumPic) {
        this.url = url;
        this.title = title;
        this.author = author;
        this.albumPic = albumPic;
    }

    @Ignore
    public MusicBean(String mid, String url, String title, String author, String album, String albumInfo, String albumPic, String totalTime) {
        this.mid = mid;
        this.url = url;
        this.title = title;
        this.author = author;
        this.album = album;
        this.albumInfo = albumInfo;
        this.albumPic = albumPic;
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "mid='" + mid + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", album='" + album + '\'' +
                ", albumInfo='" + albumInfo + '\'' +
                ", albumPic='" + albumPic + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public String audioId() {
        return mid;
    }

    @Override
    public String audioTitle() {
        return title;
    }

    @Override
    public String audioSubTitle() {
        return author + " - " + album;
    }

    @Override
    public String audioCover() {
        return albumPic;
    }

    @Override
    public String audioPath() {
        return url;
    }

    @Override
    public String author() {
        return author;
    }
}
