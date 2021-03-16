package com.yyxnb.module_wanandroid.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.common_res.constants.WanRouterPath;

@Route(path = WanRouterPath.MAIN_ACTIVITY)
public class WanActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WanMainFragment();
    }

}