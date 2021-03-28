package com.yyxnb.module_wanandroid.ui;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.WanRouterPath;

@Route(path = WanRouterPath.MAIN_ACTIVITY)
public class WanActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WanMainFragment();
    }

}