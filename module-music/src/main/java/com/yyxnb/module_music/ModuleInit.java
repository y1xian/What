package com.yyxnb.module_music;

import com.yyxnb.common_base.module.ModuleInitImpl;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.ui.MusicPlayerActivity;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.music.MusicPlayerManager;
import com.yyxnb.what.music.interfaces.MusicConstants;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.what.music.utils.MusicPlayerConfig;

public class ModuleInit extends ModuleInitImpl {

    @Override
    public void onCreate() {

        //音乐播放器配置
        MusicPlayerConfig config = MusicPlayerConfig.Build()
                //设置默认的闹钟定时关闭模式，优先取用户设置
                .setDefaultAlarmModel(MusicConstants.MUSIC_ALARM_MODEL_0)
                //设置默认的循环模式，优先取用户设置
                .setDefaultPlayModel(MusicConstants.MUSIC_MODEL_LOOP);

        //此处自行存储播放记录
        MusicPlayerManager.getInstance()
                //内部存储初始化
                .init(AppUtils.getApp())
                //应用播放器配置
                .setMusicPlayerConfig(config)
                //通知栏交互，默认开启
                .setNotificationEnable(true)
                //常驻进程开关，默认开启
                .setLockForeground(true)
                //设置点击通知栏跳转的播放器界面,需开启常驻进程开关
                .setPlayerActivityName(MusicPlayerActivity.class.getCanonicalName())
                //设置锁屏界面，如果禁用，不需要设置或者设置为null
//                .setLockActivityName(MusicLockActivity.class.getCanonicalName())
                //监听播放状态
//                .setPlayInfoListener((MusicPlayerInfoListener<MusicBean>) (musicInfo, position) ->
//                {
//                    LogUtils.w(position + " , onPlayMusicOnInfo :" + musicInfo.toString());
//
//                })
                //需在activity初始化
                //重载方法，初始化音频媒体服务,成功之后如果系统还在播放音乐，则创建一个悬浮窗承载播放器
//                .initialize(application.getApplicationContext(), new MusicInitializeCallBack() {
//
//                    @Override
//                    public void onSuccess() {
//                        //如果系统正在播放音乐
//                        if (null != MusicPlayerManager.getInstance().getCurrentPlayerMusic()) {
//                            MusicPlayerManager.getInstance().createWindowJukebox();
//                        }
//                    }
//                });
                .addOnPlayerEventListener(new MusicPlayerEventListener<MusicBean>() {
                    @Override
                    public void onMusicPlayerState(int playerState, String message) {
                        LogUtils.e("playerState : " + playerState + " , message : " + message);
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
                        LogUtils.e("onPlayMusicOnInfo：" + musicInfo.toString());
                    }

                    @Override
                    public void onMusicPathInvalid(MusicBean musicInfo, int position) {

                    }

                    @Override
                    public void onTaskRuntime(long totalDurtion, long currentDurtion, long alarmResidueDurtion, int bufferProgress) {

                    }

                    @Override
                    public void onPlayerConfig(int playModel, int alarmModel, boolean isToast) {

                    }
                });
    }
}
