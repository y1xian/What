package com.yyxnb.module_main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yyxnb.arch.base.IFragment
import com.yyxnb.module_main.R

/**
 * 分类
 */
class MainClassificationFragment : Fragment(), IFragment {

    override fun initLayoutResId(): Int {
        return R.layout.fragment_main_classification
    }

    override fun initView(savedInstanceState: Bundle?) {}

    override fun onVisible() {
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate()
    }
}