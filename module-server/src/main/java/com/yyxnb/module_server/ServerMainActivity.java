package com.yyxnb.module_server;

import androidx.fragment.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;

public class ServerMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new ServerFragment();
    }

}