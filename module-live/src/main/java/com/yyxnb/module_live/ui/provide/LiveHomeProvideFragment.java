package com.yyxnb.module_live.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.LiveRouterPath;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_live.R;
import com.yyxnb.module_live.databinding.FragmentLiveHomeProvideBinding;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的直播首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = LiveRouterPath.SHOW_FRAGMENT)
public class LiveHomeProvideFragment extends BaseFragment {

    private FragmentLiveHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_live_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}