package com.yyxnb.module_mall.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.MALL_MAIN;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：商城 主界面
 * ================================================
 */
@Route(path = MALL_MAIN)
public class MallActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new MallMainFragment();
    }
}