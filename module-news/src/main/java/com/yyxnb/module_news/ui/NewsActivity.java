package com.yyxnb.module_news.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.NewsRouterPath;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：新闻 主界面
 * ================================================
 */
@Route(path = NewsRouterPath.MAIN_ACTIVITY)
public class NewsActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new NewsMainFragment();
    }
}