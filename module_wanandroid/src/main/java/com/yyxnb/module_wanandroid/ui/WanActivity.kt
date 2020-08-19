package com.yyxnb.module_wanandroid.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN
import com.yyxnb.common_base.base.ContainerActivity

@Route(path = WAN_MAIN)
class WanActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return WanMainFragment()
    }
}