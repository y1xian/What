package com.yyxnb.module_user.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.ContainerActivity;

import static com.yyxnb.common_res.arouter.ARouterConstant.USER_MAIN;

@Route(path = USER_MAIN)
public class UserActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new UserFragment();
    }
}
