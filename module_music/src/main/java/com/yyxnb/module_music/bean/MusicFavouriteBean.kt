package com.yyxnb.module_music.bean

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.RoomWarnings
import com.yyxnb.module_music.bean.MusicBean
import java.io.Serializable

@Entity(tableName = "favourite", primaryKeys = ["uid", "mid"])
data class MusicFavouriteBean(
        // 用户id
        @NonNull
        var uid: Int = 0,

        // 是否喜欢/收藏
        var isFavourite: Boolean = false,

        @NonNull
        // 如果没有该注解，Android Studio编译的时候会报警告，但不影响编译运行
        @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
        @Embedded
        var musicBean: MusicBean = MusicBean()
) : Serializable {
    //    // 视频id
    //    @NonNull
    //    public String mid;

    override fun toString(): String {
        return "MusicFavouriteBean{" +
                "uid=" + uid +
                ", isFavourite=" + isFavourite +
                ", musicBean=" + musicBean +
                '}'
    }
}