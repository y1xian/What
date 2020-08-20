package com.yyxnb.module_music.bean

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.RoomWarnings
import java.io.Serializable

@Entity(tableName = "record", primaryKeys = ["mid"]) //        foreignKeys = @ForeignKey(entity = MusicBean.class, parentColumns = "mid", childColumns = "mid"))
data class MusicRecordBean(
        // 用户id
        @NonNull
        var uid: Int = 0,

        // 当前播放时长
        @JvmField
        var currenTime: Long = 0,

        // 添加时间
        @JvmField
        var updateTime: Long = 0,

        @NonNull
        @JvmField
        // 如果没有该注解，Android Studio编译的时候会报警告，但不影响编译运行
        @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
        @Embedded
        var musicBean: MusicBean = MusicBean()
) : Serializable {
    //    // 视频id
    //    @NonNull
    //    public String mid;


    override fun toString(): String {
        return "MusicRecordBean{" +
                "uid=" + uid +
                ", currenTime=" + currenTime +
                ", updateTime=" + updateTime +
                ", musicBean=" + musicBean +
                '}'
    }
}