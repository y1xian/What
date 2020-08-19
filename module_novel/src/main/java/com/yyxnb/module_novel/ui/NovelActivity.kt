package com.yyxnb.module_novel.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.common_base.arouter.ARouterConstant.NOVEL_MAIN
import com.yyxnb.common_base.base.ContainerActivity

@Route(path = NOVEL_MAIN)
class NovelActivity : ContainerActivity() {

    override fun initBaseFragment(): Fragment? {
        return NovelMainFragment()
    }
}