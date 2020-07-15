package com.yyxnb.lib_audio.events;


import com.yyxnb.lib_audio.core.AudioController;

/**
 * 播放模式切换事件
 */
public class AudioPlayModeEvent {
  public AudioController.PlayMode mPlayMode;

  public AudioPlayModeEvent(AudioController.PlayMode playMode) {
    this.mPlayMode = playMode;
  }
}
