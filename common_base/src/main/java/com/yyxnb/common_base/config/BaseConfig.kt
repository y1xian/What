package com.yyxnb.common_base.config

import android.os.Environment
import com.tencent.mmkv.MMKV

object BaseConfig {

    @JvmField
    val kv = MMKV.defaultMMKV()

    const val TAG = "BaseConfig"

    //存储位置
    val DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

    //保存视频的时候，在sd卡存储短视频的路径DCIM下
    val VIDEO_PATH = DCMI_PATH + "/cache/video/"

    //下载贴纸的时候保存的路径
    val VIDEO_TIE_ZHI_PATH = DCMI_PATH + "/cache/tiezhi/"

    //下载音乐的时候保存的路径
    val VIDEO_MUSIC_PATH = DCMI_PATH + "/cache/music/"

    //拍照时图片保存路径
    val CAMERA_IMAGE_PATH = DCMI_PATH + "/cache/camera/"

}