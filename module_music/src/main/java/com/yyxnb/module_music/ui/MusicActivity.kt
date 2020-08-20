package com.yyxnb.module_music.ui

import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.arch.utils.AppManager
import com.yyxnb.common_base.arouter.ARouterConstant.MUSIC_MAIN
import com.yyxnb.common_base.base.ContainerActivity

@Route(path = MUSIC_MAIN)
class MusicActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return MusicHomeFragment()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.returnDesktop(this)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}