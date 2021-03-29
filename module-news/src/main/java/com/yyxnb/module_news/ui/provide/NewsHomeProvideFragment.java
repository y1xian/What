package com.yyxnb.module_news.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.NewsRouterPath;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.module_news.R;
import com.yyxnb.module_news.databinding.FragmentNewsHomeProvideBinding;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：对外提供的新闻资讯首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = NewsRouterPath.SHOW_FRAGMENT)
public class NewsHomeProvideFragment extends BaseFragment {

    private FragmentNewsHomeProvideBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_news_home_provide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.iRv.vStatus.showEmptyView();
    }
}