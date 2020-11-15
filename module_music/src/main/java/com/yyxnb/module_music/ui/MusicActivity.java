package com.yyxnb.module_music.ui;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.lib_arch.common.AppManager;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.MUSIC_MAIN;

@Route(path = MUSIC_MAIN)
public class MusicActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MusicHomeFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            AppManager.getInstance().returnDesktop(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}