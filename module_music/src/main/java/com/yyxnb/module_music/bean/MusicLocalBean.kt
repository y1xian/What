package com.yyxnb.module_music.bean

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "local")
class MusicLocalBean  : MusicBean(), Serializable {
    //    @PrimaryKey(autoGenerate = true)
    //    public int _id;
    //    @NonNull
    //    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    //    @Embedded
    //    public MusicBean musicBean;
    override fun toString(): String {
        return super.toString()
    }
}