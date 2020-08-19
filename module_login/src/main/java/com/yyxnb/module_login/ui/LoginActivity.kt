package com.yyxnb.module_login.ui;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_ACTIVITY;

@Route(path = LOGIN_ACTIVITY)
public class LoginActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LoginFragment();
    }
}