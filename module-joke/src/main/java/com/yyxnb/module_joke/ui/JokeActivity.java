package com.yyxnb.module_joke.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

import static com.yyxnb.common_res.arouter.ARouterConstant.JOKE_MAIN;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：娱乐 主界面
 * ================================================
 */
@Route(path = JOKE_MAIN)
public class JokeActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
//        return new JokeMainFragment();
        return new JokeHomeFragment();
    }
}