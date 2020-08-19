package com.yyxnb.module_video

import android.app.Application
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.yyxnb.common_base.module.IModuleInit

class ModuleInit : IModuleInit {
    override fun onCreate(application: Application) {
        VideoViewManager.setConfig(VideoViewConfig.newBuilder() //调试的时候请打开日志，方便排错
                .setLogEnabled(false)
                .setPlayerFactory(IjkPlayerFactory.create()) //                .setEnableOrientation(true)
                .build())
    }
}