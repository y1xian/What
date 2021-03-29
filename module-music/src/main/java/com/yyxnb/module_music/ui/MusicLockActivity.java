package com.yyxnb.module_music.ui;

import android.os.Bundle;

import com.yyxnb.common_base.base.BaseActivity;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.bean.MusicBean;

/**
 * 音乐锁屏播放
 */
public class MusicLockActivity extends BaseActivity implements MusicPlayerEventListener<MusicBean> {


    @Override
    public int initLayoutResId() {
        return R.layout.activity_music_lock;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onMusicPlayerState(int playerState, String message) {

    }

    @Override
    public void onPrepared(long totalDuration) {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onInfo(int event, int extra) {

    }

    @Override
    public void onPlayMusicOnInfo(MusicBean musicInfo, int position) {

    }

    @Override
    public void onMusicPathInvalid(MusicBean musicInfo, int position) {

    }

    @Override
    public void onTaskRuntime(long totalDuration, long currentDuration, long alarmResidueDuration, int bufferProgress) {

    }

    @Override
    public void onPlayerConfig(int playModel, int alarmModel, boolean isToast) {

    }

}