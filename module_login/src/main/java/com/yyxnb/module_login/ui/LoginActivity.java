package com.yyxnb.module_login.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

import static com.yyxnb.common_res.arouter.ARouterConstant.LOGIN_MAIN;

@Route(path = LOGIN_MAIN)
public class LoginActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LoginFragment();
    }
}