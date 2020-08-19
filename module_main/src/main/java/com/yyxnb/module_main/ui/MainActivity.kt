package com.yyxnb.module_main.ui

import androidx.fragment.app.Fragment
import com.yyxnb.common_base.base.ContainerActivity

/**
 * @author yyx
 */
class MainActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return MainHomeFragment()
    }
}