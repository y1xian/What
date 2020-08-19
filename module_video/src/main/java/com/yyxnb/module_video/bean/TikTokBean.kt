package com.yyxnb.module_video.bean

import androidx.annotation.NonNull
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "video", primaryKeys = ["id", "title"])
data class TikTokBean(
        @JvmField
        @NonNull
        var id: Int = 0,
        @JvmField
        var videoId: Int = 0,
        @JvmField
        var userId: Int = 0,
        @JvmField
        var itemId: Int = 0,
        @JvmField
        var commentCount: Int = 0,
        @JvmField
        var coverUrl: String? = "",
        @JvmField
        var videoUrl: String? = "",
        @JvmField
        @NonNull
        var title: String = "",
        @JvmField
        var state: Int = 0,
        @JvmField
        var type: Int = 0,
        @JvmField
        var description: String? = null,
        @JvmField
        var likeCount: Int = 0,
        @JvmField
        var playCount: Int = 0,
        @JvmField
        var createTime: String? = null
) : Serializable {
    /**
     * id : 2
     * videoId : 2
     * userId : 1
     * itemId : 2
     * commentCount : 0
     * coverUrl : http://p3-dy.byteimg.com/large/tos-cn-p-0015/6f888eb380fc4a0084c7bf0a824c61fb_1575445410.jpeg?from=2563711402_large
     * videoUrl : ttp://v6-dy.ixigua.com/eb7e02d4284ff496da8a6ab847001da6/5de7d06e/video/tos/hxsy/tos-hxsy-ve-0015/fb4974d1663b41aba1cef7b66f98118c/?a=1128&br=939&cr=0&cs=0&dr=0&ds=6&er=&l=201912042227280100140431330D4C1DCD&lr=&qs=0&rc=anV0eGV1aDZwcTMzOmkzM0ApOThmZGdlOmRoNzg6aWY8NWc0M2psLm1mNGdfLS0vLS9zcy80YC9iYC1jLzY0XmMwMV86Yw%3D%3D
     * title : 百度地图提醒你，拐不进去使劲拐#重庆重庆
     * state : 0
     * type : 1
     * description : null
     * likeCount : 232
     * playCount : 23
     * createTime : 1575445406000
     */
    //    @PrimaryKey(autoGenerate = true)
    //    public int _id;
    //    @ColumnInfo(name ="vid")

    override fun toString(): String {
        return "TikTokBean{" +
                "id=" + id +
                ", videoId=" + videoId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", commentCount=" + commentCount +
                ", coverUrl='" + coverUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", title='" + title + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", likeCount=" + likeCount +
                ", playCount=" + playCount +
                ", createTime='" + createTime + '\'' +
                '}'
    }
}