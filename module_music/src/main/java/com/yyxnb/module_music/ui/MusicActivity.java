package com.yyxnb.module_music.ui;

import android.view.KeyEvent;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.lib_arch.common.AppManager;

import static com.yyxnb.common_res.arouter.ARouterConstant.MUSIC_MAIN;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：音乐 主界面
 * ================================================
 */
@Route(path = MUSIC_MAIN)
public class MusicActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MusicHomeFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getInstance().returnDesktop(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}