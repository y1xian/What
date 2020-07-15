package com.yyxnb.module_music.service;

import android.app.Service;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.common.ToastUtils;
import com.yyxnb.lib_audio.AudioHelper;
import com.yyxnb.lib_audio.bean.AudioBean;
import com.yyxnb.lib_audio.events.AudioEvent;

import java.util.ArrayList;
import java.util.Objects;

import static com.yyxnb.lib_audio.AudioConfig.EVENT_AUDIO_KEY;
import static com.yyxnb.module_music.service.NotificationHelper.NOTIFICATION_ID;


/**
 * 音乐后台服务,并更新notification状态
 */
public class MusicService extends Service implements NotificationHelper.NotificationHelperListener {

    private final static String DATA_AUDIOS = "AUDIOS";
    //actions
    private final static String ACTION_START = "ACTION_START";

    private ArrayList<AudioBean> mAudioBeans;

    private NotificationReceiver mReceiver;

    /**
     * 外部直接service方法
     */
    public static void startMusicService() {
        Intent intent = new Intent(AudioHelper.getContext(), MusicService.class);
        intent.setAction(ACTION_START);
        AudioHelper.getContext().startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LiveEventBus.get(EVENT_AUDIO_KEY).observeForever(mAudioEvent);
        registerBroadcastReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            //初始化前台Notification
            NotificationHelper.getInstance().init(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void registerBroadcastReceiver() {
        if (mReceiver == null) {
            mReceiver = new NotificationReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(NotificationReceiver.ACTION_STATUS_BAR);
            registerReceiver(mReceiver, filter);
        }
    }

    private void unRegisterBroadcastReceiver() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onNotificationInit() {
        //service与Notification绑定
        startForeground(NOTIFICATION_ID, NotificationHelper.getInstance().getNotification());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LiveEventBus.get(EVENT_AUDIO_KEY).removeObserver(mAudioEvent);
        unRegisterBroadcastReceiver();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioLoadEvent(AudioLoadEvent event) {
//        //更新notifacation为load状态
//        NotificationHelper.getInstance().showLoadStatus(event.mAudioBean);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioPauseEvent(AudioPauseEvent event) {
//        //更新notifacation为暂停状态
//        NotificationHelper.getInstance().showPauseStatus();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioStartEvent(AudioStartEvent event) {
//        //更新notifacation为播放状态
//        NotificationHelper.getInstance().showPlayStatus();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioFavouriteEvent(AudioFavouriteEvent event) {
//        //更新notifacation收藏状态
//        NotificationHelper.getInstance().changeFavouriteStatus(event.isFavourite);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioReleaseEvent(AudioReleaseEvent event) {
//        //移除notifacation
//    }

    @SuppressWarnings("rawtypes")
    private final Observer mAudioEvent = (Observer<AudioEvent>) event -> {
        switch (Objects.requireNonNull(event).audioState) {
            case AudioEvent.AudioState.DATA:
                //更新数据源
                mAudioBeans = event.mAudioBeanList;
                break;
            case AudioEvent.AudioState.LOAD:
                //更新notifacation为load状态
                NotificationHelper.getInstance().showLoadStatus(event.mAudioBean);
                break;
            case AudioEvent.AudioState.COMPLETE:
                //插放完毕事件处理
                break;
            case AudioEvent.AudioState.ERROR:
                //播放出错事件处理
                break;
            case AudioEvent.AudioState.START:
                //更新notifacation为播放状态
                NotificationHelper.getInstance().showPlayStatus();
                break;
            case AudioEvent.AudioState.PAUSE:
                //更新notifacation为暂停状态
                NotificationHelper.getInstance().showPauseStatus();
                break;
            case AudioEvent.AudioState.RELEASE:
                //移除notifacation
                break;
            case AudioEvent.AudioState.PROGRESS:
                //更新当前view的播放进度
                break;
            default:
                break;
        }
    };

    /**
     * 接收Notification发送的广播
     */
    public static class NotificationReceiver extends BroadcastReceiver {
        public static final String ACTION_STATUS_BAR =
                AudioHelper.getContext().getPackageName() + ".NOTIFICATION_ACTIONS";
        public static final String EXTRA = "extra";
        public static final String EXTRA_PLAY = "play_pause";
        public static final String EXTRA_NEXT = "play_next";
        public static final String EXTRA_PRE = "play_previous";
        public static final String EXTRA_FAV = "play_favourite";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            String extra = intent.getStringExtra(EXTRA);
            switch (extra) {
                case EXTRA_PLAY:
                    //处理播放暂停事件,可以封到AudioController中
                    AudioHelper.controller().playOrPause();
                    break;
                case EXTRA_PRE:
                    AudioHelper.controller().previous(); //不管当前状态，直接播放
                    break;
                case EXTRA_NEXT:
                    AudioHelper.controller().next();
                    break;
                case EXTRA_FAV:
                    ToastUtils.normal("收藏");
//                    AudioController.getInstance().changeFavourite();
                    break;
                default:
                    break;
            }
        }
    }
}
