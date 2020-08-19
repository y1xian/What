package com.yyxnb.module_user.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.USER_ACTIVITY
import com.yyxnb.common_base.base.ContainerActivity

@Route(path = USER_ACTIVITY)
class UserActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return UserFragment()
    }
}