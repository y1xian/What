package com.yyxnb.what.music.utils;

import com.yyxnb.what.music.interfaces.MusicConstants;

/**
 * MusicPlayer Config
 */
public class MusicPlayerConfig {

    //默认闹钟模式
    private int defaultAlarmModel = MusicConstants.MUSIC_ALARM_MODEL_0;
    //默认播放模式
    private int defaultPlayModel = MusicConstants.MUSIC_MODEL_LOOP;

    public int getDefaultAlarmModel() {
        return defaultAlarmModel;
    }

    public MusicPlayerConfig setDefaultAlarmModel(int defaultAlarmModel) {
        this.defaultAlarmModel = defaultAlarmModel;
        return this;
    }

    public int getDefaultPlayModel() {
        return defaultPlayModel;
    }

    public MusicPlayerConfig setDefaultPlayModel(int defaultPlayModel) {
        this.defaultPlayModel = defaultPlayModel;
        return this;
    }

    public static MusicPlayerConfig Build() {
        return new MusicPlayerConfig();
    }
}