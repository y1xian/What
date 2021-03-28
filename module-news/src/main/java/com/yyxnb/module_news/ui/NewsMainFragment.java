package com.yyxnb.module_news.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.NewsRouterPath;
import com.yyxnb.module_news.R;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：新闻资讯 主界面
 * ================================================
 */
@Route(path = NewsRouterPath.MAIN_FRAGMENT)
public class NewsMainFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_news_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}