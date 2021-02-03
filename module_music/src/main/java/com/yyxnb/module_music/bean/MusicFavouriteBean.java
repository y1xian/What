package com.yyxnb.module_music.bean;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.RoomWarnings;

import java.io.Serializable;

@Entity(tableName = "favourite", primaryKeys = {"uid", "mid"})
public class MusicFavouriteBean implements Serializable {

//    // 视频id
//    @NonNull
//    public String mid;

    // 用户id
    public int uid;

    // 是否喜欢/收藏
    public boolean isFavourite;

    @NonNull
    // 如果没有该注解，Android Studio编译的时候会报警告，但不影响编译运行
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    public MusicBean musicBean;

    @Override
    public String toString() {
        return "MusicFavouriteBean{" +
                "uid=" + uid +
                ", isFavourite=" + isFavourite +
                ", musicBean=" + musicBean +
                '}';
    }
}
