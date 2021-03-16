package com.yyxnb.module_novel.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;
import com.yyxnb.common_res.constants.NovelRouterPath;

@Route(path = NovelRouterPath.MAIN_ACTIVITY)
public class NovelActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new NovelMainFragment();
    }
}