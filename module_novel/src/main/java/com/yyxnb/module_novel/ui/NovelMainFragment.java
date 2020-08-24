package com.yyxnb.module_novel.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_novel.R;

import static com.yyxnb.common_base.arouter.ARouterConstant.NOVEL_MAIN_FRAGMENT;

/**
 * 小说主页.
 */
@Route(path = NOVEL_MAIN_FRAGMENT)
public class NovelMainFragment extends BaseFragment {


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_novel_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}