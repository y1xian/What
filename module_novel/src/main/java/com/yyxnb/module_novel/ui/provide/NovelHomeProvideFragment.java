package com.yyxnb.module_novel.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.databinding.FragmentNovelHomeProvideBinding;

import static com.yyxnb.common_res.arouter.ARouterConstant.NOVEL_HOME_PROVIDE_FRAGMENT;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的小说首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = NOVEL_HOME_PROVIDE_FRAGMENT)
public class NovelHomeProvideFragment extends BaseFragment {

    private FragmentNovelHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_novel_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}