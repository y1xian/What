package com.yyxnb.module_main.ui;

import androidx.fragment.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.module_main.ui.main.MainFragment;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/23
 * 历    史：
 * 描    述：主界面
 * ================================================
 */
public class MainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MainFragment();
//        return new MainHomeFragment();
//        return new MainTestFragment();
    }

}
