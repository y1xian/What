package com.yyxnb.module_music

import android.app.Application
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common_base.module.IModuleInit
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.lib_music.interfaces.MusicConstants
import com.yyxnb.lib_music.interfaces.MusicPlayerEventListener
import com.yyxnb.lib_music.utils.MusicPlayerConfig
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.ui.MusicPlayerActivity

class ModuleInit : IModuleInit {
    override fun onCreate(application: Application) {

        //音乐播放器配置
        val config = MusicPlayerConfig.Build() //设置默认的闹钟定时关闭模式，优先取用户设置
                .setDefaultAlarmModel(MusicConstants.MUSIC_ALARM_MODEL_0) //设置默认的循环模式，优先取用户设置
                .setDefaultPlayModel(MusicConstants.MUSIC_MODEL_LOOP)

        //此处自行存储播放记录
        MusicPlayerManager.getInstance() //内部存储初始化
                .init(application.applicationContext) //应用播放器配置
                .setMusicPlayerConfig(config) //通知栏交互，默认开启
                .setNotificationEnable(true) //常驻进程开关，默认开启
                .setLockForeground(true) //设置点击通知栏跳转的播放器界面,需开启常驻进程开关
                .setPlayerActivityName(MusicPlayerActivity::class.java.canonicalName) //设置锁屏界面，如果禁用，不需要设置或者设置为null
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
                .addOnPlayerEventListener(object : MusicPlayerEventListener<MusicBean> {
                    override fun onMusicPlayerState(playerState: Int, message: String) {
                        e("playerState : $playerState , message : $message")
                        Thread { e("playerState222 : $playerState , message : $message") }.start()
                    }

                    override fun onPrepared(totalDuration: Long) {}
                    override fun onBufferingUpdate(percent: Int) {}
                    override fun onInfo(event: Int, extra: Int) {}
                    override fun onPlayMusicOnInfo(musicInfo: MusicBean, position: Int) {
                        e("onPlayMusicOnInfo：$musicInfo")
                    }

                    override fun onMusicPathInvalid(musicInfo: MusicBean, position: Int) {}
                    override fun onTaskRuntime(totalDurtion: Long, currentDurtion: Long, alarmResidueDurtion: Long, bufferProgress: Int) {}
                    override fun onPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {}
                })
    }
}