package com.yyxnb.what.localservice.bean;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;


public class LocalMedia implements Serializable {

    private String path; // 文件路径
    private String name; // 文件名称
    private String extension;
    private long time; //时间戳
    private int mediaType; //类型 audio, video, image or playlist
    private long size; //大小
    private long duration;   //时长
    private long id;//id标识
    private String parentDir; //文件夹
    private String mimeType; //文件的MIME类型
    private String artist; // 艺术家
    private String title; // 显示名称
    private int selectPosition; //当图片选择后，索引值
    private int position; //当前图片在列表中顺序

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalMedia)) {
            return false;
        }
        LocalMedia that = (LocalMedia) o;
        return getTime() == that.getTime() &&
                getMediaType() == that.getMediaType() &&
                getSize() == that.getSize() &&
                getDuration() == that.getDuration() &&
                getId() == that.getId() &&
                getSelectPosition() == that.getSelectPosition() &&
                getPosition() == that.getPosition() &&
                Objects.equals(getPath(), that.getPath()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getExtension(), that.getExtension()) &&
                Objects.equals(getParentDir(), that.getParentDir()) &&
                Objects.equals(getMimeType(), that.getMimeType()) &&
                Objects.equals(getArtist(), that.getArtist()) &&
                Objects.equals(getTitle(), that.getTitle());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(getPath(), getName(), getExtension(), getTime(), getMediaType(), getSize(), getDuration(), getId(), getParentDir(), getMimeType(), getArtist(), getTitle(), getSelectPosition(), getPosition());
    }
}
