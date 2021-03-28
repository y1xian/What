package com.yyxnb.module_mall.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.MallRouterPath;
import com.yyxnb.module_mall.R;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：商城 主界面
 * ================================================
 */
@Route(path = MallRouterPath.MAIN_FRAGMENT)
public class MallMainFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_mall_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}