package com.yyxnb.module_main.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.arch.annotations.SwipeBack

import com.yyxnb.module_main.R

/**
 * A simple [Fragment] subclass.
 */
@SwipeBack(value = -1)
class MainClassificationFragment : BaseFragment() {

    override fun initLayoutResId(): Int = R.layout.fragment_main_classification

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initStatusBarColor(): Int {
        return resources.getColor(R.color.red)
    }
}
