package com.yyxnb.module_novel.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

import static com.yyxnb.common_res.arouter.ARouterConstant.NOVEL_MAIN;

@Route(path = NOVEL_MAIN)
public class NovelActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new NovelMainFragment();
    }
}