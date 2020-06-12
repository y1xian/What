package com.yyxnb.module_login;

import android.support.v4.app.Fragment;

import com.yyxnb.arch.ContainerActivity;

public class LoginActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LoginFragment();
    }
}