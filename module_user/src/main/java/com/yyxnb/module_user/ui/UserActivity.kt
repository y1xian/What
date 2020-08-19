package com.yyxnb.module_user.ui;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

@Route(path = "/user/userMain/ac")
public class UserActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new UserFragment();
    }
}
