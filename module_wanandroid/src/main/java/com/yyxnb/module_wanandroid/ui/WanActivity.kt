package com.yyxnb.module_wanandroid.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.core.ContainerActivity
import com.yyxnb.common_res.arouter.ARouterConstant

@Route(path = ARouterConstant.WAN_MAIN)
class WanActivity : ContainerActivity() {
    override fun initBaseFragment(): Fragment {
        return WanMainFragment()
    }
}