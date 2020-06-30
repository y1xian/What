package com.yyxnb.module_joke.fragments;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_joke.R;

import static com.yyxnb.common_base.arouter.ARouterConstant.JOKE_MAIN_FRAGMENT;

/**
 * joke - 主页.
 */
@Route(path = JOKE_MAIN_FRAGMENT)
public class JokeMainFragment extends BaseFragment {


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}