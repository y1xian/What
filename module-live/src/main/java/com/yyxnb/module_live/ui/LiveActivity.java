package com.yyxnb.module_live.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.LiveRouterPath;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：主播 主界面
 * ================================================
 */
@Route(path = LiveRouterPath.MAIN_ACTIVITY)
public class LiveActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LiveMainFragment();
    }
}