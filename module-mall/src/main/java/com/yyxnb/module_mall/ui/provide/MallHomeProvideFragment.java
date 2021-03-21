package com.yyxnb.module_mall.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.MallRouterPath;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_mall.R;
import com.yyxnb.module_mall.databinding.FragmentMallHomeProvideBinding;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的商城首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = MallRouterPath.SHOW_FRAGMENT)
public class MallHomeProvideFragment extends BaseFragment {

    private FragmentMallHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_mall_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}