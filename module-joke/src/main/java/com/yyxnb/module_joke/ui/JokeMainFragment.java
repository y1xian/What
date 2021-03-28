package com.yyxnb.module_joke.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.JokeRouterPath;
import com.yyxnb.module_joke.R;
import com.yyxnb.module_joke.databinding.FragmentJokeMainBinding;

/**
 * joke - 主页.
 */
@Route(path = JokeRouterPath.MAIN_FRAGMENT)
public class JokeMainFragment extends BaseFragment {

    private FragmentJokeMainBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
    }
}