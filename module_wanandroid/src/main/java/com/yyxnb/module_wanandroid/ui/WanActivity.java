package com.yyxnb.module_wanandroid.ui;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN;

@Route(path = WAN_MAIN)
public class WanActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new WanMainFragment();
    }

}