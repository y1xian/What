package com.yyxnb.lib_audio.events;


import com.yyxnb.lib_audio.bean.AudioBean;

public class AudioLoadEvent {
  public AudioBean mAudioBean;

  public AudioLoadEvent(AudioBean audioBean) {
    this.mAudioBean = audioBean;
  }
}
