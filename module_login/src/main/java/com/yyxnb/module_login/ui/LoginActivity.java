package com.yyxnb.module_login.ui;

import android.support.v4.app.Fragment;

import com.yyxnb.common_base.base.ContainerActivity;


public class LoginActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LoginFragment();
    }
}