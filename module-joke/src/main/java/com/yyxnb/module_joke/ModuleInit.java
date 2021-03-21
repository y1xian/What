package com.yyxnb.module_joke;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.yyxnb.common_base.module.ModuleInitImpl;
import com.yyxnb.what.app.AppUtils;

public class ModuleInit extends ModuleInitImpl {

    @Override
    public void onCreate() {

        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //调试的时候请打开日志，方便排错
                .setLogEnabled(AppUtils.isDebug())
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setEnableOrientation(true)
                .build());

    }
}
