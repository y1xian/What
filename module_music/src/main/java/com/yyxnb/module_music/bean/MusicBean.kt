package com.yyxnb.module_music.bean

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.yyxnb.lib_music.interfaces.IMusic
import java.io.Serializable

/**
 * 歌曲实体
 */
@Entity(tableName = "music")
open class MusicBean(

        // 视频id
        @JvmField
        @NonNull
        @PrimaryKey
        var mid: String = "",

        // 地址
        @JvmField
        var url: String = "",

        // 歌名
        @JvmField
        var title: String = "",

        // 作者
        @JvmField
        var author: String = "",

        // 所属专辑
        @JvmField
        var album: String = "",

        // 信息
        @JvmField
        var albumInfo: String = "",

        // 封面
        @JvmField
        var albumPic: String = "",

        // 时长
        @JvmField
        var totalTime: String = "",

        // 类型
        @JvmField
        var type: String = ""

) : IMusic, Serializable {


//    @Ignore
//    constructor() {
//    }
//
//    @Ignore
//    constructor(mid: String, url: String, title: String, author: String, album: String, albumPic: String) {
//        this.mid = mid
//        this.url = url
//        this.title = title
//        this.author = author
//        this.album = album
//        this.albumPic = albumPic
//    }
//
//    @Ignore
//    constructor(url: String, title: String, author: String, albumPic: String) {
//        this.url = url
//        this.title = title
//        this.author = author
//        this.albumPic = albumPic
//    }
//
//    @Ignore
//    constructor(mid: String, url: String, title: String, author: String, album: String, albumInfo: String, albumPic: String, totalTime: String) {
//        this.mid = mid
//        this.url = url
//        this.title = title
//        this.author = author
//        this.album = album
//        this.albumInfo = albumInfo
//        this.albumPic = albumPic
//        this.totalTime = totalTime
//    }

    override fun toString(): String {
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
                '}'
    }

    override fun audioId(): String {
        return mid
    }

    override fun audioTitle(): String {
        return title
    }

    override fun audioSubTitle(): String {
        return "$author - $album"
    }

    override fun audioCover(): String {
        return albumPic
    }

    override fun audioPath(): String {
        return url
    }

    override fun author(): String {
        return author
    }
}