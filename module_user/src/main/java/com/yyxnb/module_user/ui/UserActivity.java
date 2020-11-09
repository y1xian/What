package com.yyxnb.module_user.ui;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.USER_MAIN;

@Route(path = USER_MAIN)
public class UserActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new UserFragment();
    }
}
