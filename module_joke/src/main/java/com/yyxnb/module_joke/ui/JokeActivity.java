package com.yyxnb.module_joke.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.JOKE_MAIN;

@Route(path = JOKE_MAIN)
public class JokeActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
//        return new JokeMainFragment();
        return new JokeHomeFragment();
    }
}