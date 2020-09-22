package com.yyxnb.module_novel.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.NOVEL_MAIN_FRAGMENT
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_novel.R

/**
 * 小说主页.
 */
@Route(path = NOVEL_MAIN_FRAGMENT)
class NovelMainFragment : BaseFragment() {

    override fun initLayoutResId(): Int {
        return R.layout.fragment_novel_main
    }

    override fun initView(savedInstanceState: Bundle?) {}
}