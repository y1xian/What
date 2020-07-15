package com.yyxnb.lib_audio;

import android.content.Context;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.lib_audio.core.AudioController;

/**
 * 唯一与外界通信的帮助类
 */
public class AudioHelper {

    //SDK全局Context, 供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        LiveEventBus.config()
                //配置是否打印日志（默认打印日志）
                .enableLogger(false);
    }

    public static void pauseAudio() {
        controller().pause();
    }

    public static void resumeAudio() {
        controller().resume();
    }

    public static AudioController controller() {
        return AudioController.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }

}
