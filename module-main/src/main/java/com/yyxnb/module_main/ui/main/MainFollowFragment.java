package com.yyxnb.module_main.ui.main;

import android.os.Bundle;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.databinding.FragmentMainFollowBinding;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/24
 * 描    述：主页 - 关注
 * ================================================
 */
@BindRes(subPage = true)
public class MainFollowFragment extends BaseFragment {

    private FragmentMainFollowBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_follow;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {
        binding = getBinding();

        binding.iRvContent.vStatus.showEmptyView();
    }
}