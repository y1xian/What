package com.yyxnb.common_res.config;

import android.os.Environment;

public class BaseConfig {

    private volatile static BaseConfig config;

    public static BaseConfig getInstance() {
        if (config == null) {
            synchronized (BaseConfig.class) {
                if (config == null) {
                    config = new BaseConfig();
                }
            }
        }
        return config;
    }

    private static final String TAG = "BaseConfig";

    //存储位置
    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    //保存视频的时候，在sd卡存储短视频的路径DCIM下
    public static final String VIDEO_PATH = DCMI_PATH + "/cache/video/";
    //下载贴纸的时候保存的路径
    public static final String VIDEO_TIE_ZHI_PATH = DCMI_PATH + "/cache/tiezhi/";
    //下载音乐的时候保存的路径
    public static final String VIDEO_MUSIC_PATH = DCMI_PATH + "/cache/music/";
    //拍照时图片保存路径
    public static final String CAMERA_IMAGE_PATH = DCMI_PATH + "/cache/camera/";

    // 极速api
    public static final String JISU_APPKEY = "5fd2c586cd35cf4f";

}
