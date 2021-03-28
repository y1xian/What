package com.yyxnb.module_user.ui;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.ContainerActivity;
import com.yyxnb.common_res.constants.UserRouterPath;

@Route(path = UserRouterPath.MAIN_ACTIVITY)
public class UserActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
        return new UserFragment();
    }
}
