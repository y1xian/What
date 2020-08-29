package com.yyxnb.lib_music.interfaces

interface IMusic {
    /**
     * id
     */
    fun audioId(): String

    /**
     * 标题
     */
    fun audioTitle(): String

    /**
     * 子标题
     */
    fun audioSubTitle(): String

    /**
     * 封面
     */
    fun audioCover(): String

    /**
     * 播放地址
     */
    fun audioPath(): String

    /**
     * 作者
     */
    fun author(): String
}