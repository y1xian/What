package com.yyxnb.module_joke.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.JokeRouterPath;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/11/30
 * 描    述：娱乐 主界面
 * ================================================
 */
@Route(path = JokeRouterPath.MAIN_ACTIVITY)
public class JokeActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
//        return new JokeMainFragment();
        return new JokeHomeFragment();
    }
}