package com.yyxnb.module_user;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.ContainerActivity;
import com.yyxnb.module_user.fragments.UserFragment;

@Route(path = "/user/userMain/ac")
public class UserActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new UserFragment();
    }
}
