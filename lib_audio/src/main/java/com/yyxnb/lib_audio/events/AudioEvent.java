package com.yyxnb.lib_audio.events;


import android.support.annotation.IntDef;

import com.yyxnb.lib_audio.bean.AudioBean;
import com.yyxnb.lib_audio.core.AudioController;
import com.yyxnb.lib_audio.core.CustomMediaPlayer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * 事件处理
 */
public class AudioEvent {

    @IntDef({AudioState.DATA, AudioState.LOAD, AudioState.COMPLETE, AudioState.ERROR, AudioState.PLAY_MODE,
            AudioState.START, AudioState.PAUSE, AudioState.RELEASE, AudioState.PROGRESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioState {
        /**
         * 数据源
         */
        int DATA = 0;
        /**
         * 加载
         */
        int LOAD = 1;
        /**
         * 播放完成
         */
        int COMPLETE = 2;
        /**
         * 错误
         */
        int ERROR = 3;
        /**
         * 播放模式
         */
        int PLAY_MODE = 4;
        /**
         * 播放
         */
        int START = 5;
        /**
         * 暂停
         */
        int PAUSE = 6;
        /**
         * 销毁
         */
        int RELEASE = 7;
        /**
         * 播放进度
         */
        int PROGRESS = 8;
    }

    public int audioState;
    public AudioBean mAudioBean;
    public ArrayList<AudioBean> mAudioBeanList;

    public CustomMediaPlayer.Status mStatus;
    public AudioController.PlayMode mPlayMode;
    public int progress;
    public int maxLength;

    public AudioEvent(int audioState, ArrayList<AudioBean> mAudioBeanList) {
        this.audioState = audioState;
        this.mAudioBeanList = mAudioBeanList;
    }

    public AudioEvent(int audioState, AudioBean mAudioBean) {
        this.audioState = audioState;
        this.mAudioBean = mAudioBean;
    }

    public AudioEvent(int audioState) {
        this.audioState = audioState;
    }

    public AudioEvent(int audioState, AudioController.PlayMode mPlayMode) {
        this.audioState = audioState;
        this.mPlayMode = mPlayMode;
    }

    public AudioEvent(int audioState, CustomMediaPlayer.Status mStatus, int progress, int maxLength) {
        this.audioState = audioState;
        this.mStatus = mStatus;
        this.progress = progress;
        this.maxLength = maxLength;
    }
}
