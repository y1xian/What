package com.yyxnb.module_music.bean;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.RoomWarnings;

import java.io.Serializable;

@Entity(tableName = "record", primaryKeys = {"mid"})
//        foreignKeys = @ForeignKey(entity = MusicBean.class, parentColumns = "mid", childColumns = "mid"))
public class MusicRecordBean implements Serializable {

//    // 视频id
//    @NonNull
//    public String mid;

    // 用户id
    public int uid;

    // 当前播放时长
    public long currenTime;

    // 添加时间
    public long updateTime;

    @NonNull
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    public MusicBean musicBean;

    @Override
    public String toString() {
        return "MusicRecordBean{" +
                "uid=" + uid +
                ", currenTime=" + currenTime +
                ", updateTime=" + updateTime +
                ", musicBean=" + musicBean +
                '}';
    }
}
