package com.yyxnb.module_wanandroid.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_wanandroid.R;

import static com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN_FRAGMENT;

/**
 * 玩安卓 主页.
 */
@Route(path = WAN_MAIN_FRAGMENT)
@BindRes
public class WanMainFragment extends BaseFragment {


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}