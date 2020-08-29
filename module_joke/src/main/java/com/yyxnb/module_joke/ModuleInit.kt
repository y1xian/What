package com.yyxnb.module_joke

import android.app.Application
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.yyxnb.common_base.module.IModuleInit
import com.yyxnb.widget.WidgetManager

class ModuleInit : IModuleInit {
    override fun onCreate(application: Application) {

        VideoViewManager.setConfig(VideoViewConfig.newBuilder() //调试的时候请打开日志，方便排错
                .setLogEnabled(WidgetManager.isDebug)
                .setPlayerFactory(IjkPlayerFactory.create())
                .setEnableOrientation(true)
                .build())
    }
}