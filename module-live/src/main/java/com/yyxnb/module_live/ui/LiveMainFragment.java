package com.yyxnb.module_live.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.LiveRouterPath;
import com.yyxnb.module_live.R;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：直播 主界面
 * ================================================
 */
@Route(path = LiveRouterPath.MAIN_FRAGMENT)
public class LiveMainFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_live_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}