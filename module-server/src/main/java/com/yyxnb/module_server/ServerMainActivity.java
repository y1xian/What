package com.yyxnb.module_server;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

@Route(path = "/server/ServerActivity")
public class ServerMainActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new ServerFragment();
    }

}