package com.yyxnb.module_login.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_ACTIVITY
import com.yyxnb.common_base.base.ContainerActivity

@Route(path = LOGIN_ACTIVITY)
class LoginActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return LoginFragment()
    }
}