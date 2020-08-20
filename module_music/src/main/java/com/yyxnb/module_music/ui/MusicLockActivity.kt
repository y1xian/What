package com.yyxnb.module_music.ui

import android.os.Bundle
import com.yyxnb.common_base.base.BaseActivity
import com.yyxnb.lib_music.interfaces.MusicPlayerEventListener
import com.yyxnb.module_music.R
import com.yyxnb.module_music.bean.MusicBean

/**
 * 音乐锁屏播放
 */
class MusicLockActivity : BaseActivity(), MusicPlayerEventListener<MusicBean> {
    override fun initLayoutResId(): Int {
        return R.layout.activity_music_lock
    }

    override fun initView(savedInstanceState: Bundle?) {}
    override fun onMusicPlayerState(playerState: Int, message: String) {}
    override fun onPrepared(totalDuration: Long) {}
    override fun onBufferingUpdate(percent: Int) {}
    override fun onInfo(event: Int, extra: Int) {}
    override fun onPlayMusicOnInfo(musicInfo: MusicBean, position: Int) {}
    override fun onMusicPathInvalid(musicInfo: MusicBean, position: Int) {}
    override fun onTaskRuntime(totalDuration: Long, currentDuration: Long, alarmResidueDuration: Long, bufferProgress: Int) {}
    override fun onPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {}
}