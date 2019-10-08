package com.yyxnb.module_mian.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.arch.base.BaseFragment

import com.yyxnb.module_mian.R

/**
 * A simple [Fragment] subclass.
 */
class MainClassificationFragment : BaseFragment() {

    override fun initLayoutResId(): Int = R.layout.fragment_main_classification

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initStatusBarColor(): Int {
        return resources.getColor(R.color.red)
    }
}
