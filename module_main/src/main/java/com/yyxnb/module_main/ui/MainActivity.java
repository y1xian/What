package com.yyxnb.module_main.ui;

import android.support.v4.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;

/**
 * @author yyx
 */
public class MainActivity extends ContainerActivity {
    @Override
    public Fragment initBaseFragment() {
        return new MainHomeFragment();
    }

}
