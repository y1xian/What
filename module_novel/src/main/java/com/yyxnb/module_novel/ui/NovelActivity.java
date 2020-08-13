package com.yyxnb.module_novel.ui;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.NOVEL_MAIN;

@Route(path = NOVEL_MAIN)
public class NovelActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new NovelMainFragment();
    }
}