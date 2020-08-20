package com.yyxnb.module_video.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.common.Bus.observe
import com.yyxnb.common_base.arouter.ARouterConstant.VIDEO_MAIN_FRAGMENT
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH
import com.yyxnb.common_base.weight.NoScrollViewPager
import com.yyxnb.module_video.R
import com.yyxnb.module_video.databinding.FragmentVideoMainBinding
import com.yyxnb.module_video.ui.user.VideoUserFragment
import java.util.*

/**
 * 视频首页
 */
@BindRes
@Route(path = VIDEO_MAIN_FRAGMENT)
class VideoMainFragment : BaseFragment() {

    private var binding: FragmentVideoMainBinding? = null
    private var mViewPager: NoScrollViewPager? = null
    private var fragments: MutableList<Fragment>? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mViewPager = binding!!.mViewPager
        mViewPager!!.setNoScroll(true)
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(VideoMainBottomFragment())
            fragments?.add(VideoUserFragment.newInstance(false))
        }
        mViewPager!!.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(i: Int): Fragment {
                return fragments!![i]
            }

            override fun getCount(): Int {
                return fragments!!.size
            }
        }
    }

    override fun initObservable() {
        observe(this, { (code, _, data) ->
            when (code) {
                KEY_VIDEO_BOTTOM_VP -> mViewPager!!.setNoScroll((data as Boolean?)!!)
                KEY_VIDEO_BOTTOM_VP_SWITCH -> mViewPager!!.setCurrentItem((data as Int?)!!, true)
            }
        })
    }
}