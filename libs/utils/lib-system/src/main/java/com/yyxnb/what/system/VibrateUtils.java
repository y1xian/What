package com.yyxnb.what.system;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

import java.io.IOException;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 提示音，震动帮助类
 */
public class VibrateUtils {

    private static Vibrator vibrator;

    private static final float BEEP_VOLUME = 0.50f;
    private static final int VIBRATE_DURATION = 200;
    private static boolean playBeep = false;
    private static MediaPlayer mediaPlayer;

    /**
     * 提示音
     *
     * @param mContext
     * @param vibrate  是否震动
     * @param rawResId 音频文件（放raw包，R.raw.x）
     */
    public static void playBeep(Activity mContext, boolean vibrate, int rawResId) {
        playBeep = true;
        AudioManager audioService = (AudioManager) mContext.getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        } else {
            mContext.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.seekTo(0);
                }
            });

            AssetFileDescriptor file = mContext.getResources().openRawResourceFd(rawResId);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
        if (vibrate) {
            vibrateOnce(mContext, VIBRATE_DURATION);
        }
    }

    /**
     * 简单震动
     *
     * @param context     调用震动的Context
     * @param millisecond 震动的时间，毫秒
     */
    public static void vibrateOnce(Context context, int millisecond) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(millisecond);
    }

    /**
     * 复杂的震动
     *
     * @param context 调用震动的Context
     * @param pattern 震动形式
     *                数组参数意义：
     *                第一个参数为等待指定时间后开始震动，
     *                震动时间为第二个参数。
     *                后边的参数依次为等待震动和震动的时间
     * @param repeate 震动的次数，-1不重复，非-1为从pattern的指定下标开始重复 0为一直震动
     */
    @SuppressWarnings("static-access")
    public static void vibrateComplicated(Context context, long[] pattern, int repeate) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeate);
    }

    /**
     * 停止震动
     */
    public static void vibrateStop() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}