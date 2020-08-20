package com.yyxnb.module_video.ui

import android.os.Bundle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R

/**
 * 消息
 */
@BindRes(subPage = true)
class VideoMessageFragment : BaseFragment() {

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_message
    }

    override fun initView(savedInstanceState: Bundle?) {}

    override fun initViewData() {}

    override fun onVisible() {
        w("msg v")
    }

    override fun onInVisible() {
        w("msg iv")
    }
}