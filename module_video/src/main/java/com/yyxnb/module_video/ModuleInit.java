package com.yyxnb.module_video;

import android.app.Application;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common_base.module.IModuleInit;

public class ModuleInit implements IModuleInit {

    @Override
    public void onCreate(Application application) {
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //调试的时候请打开日志，方便排错
                .setLogEnabled(AppConfig.getInstance().isDebug())
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setEnableOrientation(true)
                .build());

    }
}
