package com.yyxnb.module_music;

import android.app.Application;

import com.yyxnb.common_base.module.IModuleInit;
import com.yyxnb.lib_audio.AudioHelper;

public class ModuleInit implements IModuleInit {

    @Override
    public void onCreate(Application application) {

        // 初始化audio
        AudioHelper.init(application.getApplicationContext());
    }
}
