package com.yyxnb.module_joke.fragments;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_joke.R;

import static com.yyxnb.common_base.arouter.ARouterConstant.JOKE_HOME_FRAGMENT;

/**
 * joke 首页.
 */
@Route(path = JOKE_HOME_FRAGMENT)
public class JokeHomeFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}