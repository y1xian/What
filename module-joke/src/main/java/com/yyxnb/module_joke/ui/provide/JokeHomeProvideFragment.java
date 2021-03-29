package com.yyxnb.module_joke.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.JokeRouterPath;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_joke.R;
import com.yyxnb.module_joke.databinding.FragmentJokeHomeProvideBinding;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的娱乐首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = JokeRouterPath.SHOW_FRAGMENT)
public class JokeHomeProvideFragment extends BaseFragment {

    private static final String TAG = "JokeHomeProvideFragment";
    private FragmentJokeHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}