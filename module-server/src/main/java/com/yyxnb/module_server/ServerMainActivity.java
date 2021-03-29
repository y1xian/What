package com.yyxnb.module_server;

import android.support.v4.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;

public class ServerMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new ServerFragment();
    }

}