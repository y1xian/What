package com.yyxnb.module_login.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.LoginRouterPath;

@Route(path = LoginRouterPath.MAIN_ACTIVITY)
public class LoginActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new LoginFragment();
    }
}