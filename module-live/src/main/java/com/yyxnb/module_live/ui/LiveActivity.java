package com.yyxnb.module_live.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

import static com.yyxnb.common_res.arouter.ARouterConstant.LIVE_MAIN;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：主播 主界面
 * ================================================
 */
@Route(path = LIVE_MAIN)
public class LiveActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LiveMainFragment();
    }
}