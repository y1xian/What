package com.yyxnb.module_video.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.arch.common.Bus.observe
import com.yyxnb.common_base.arouter.ARouterConstant.VIDEO_VIDEO
import com.yyxnb.common_base.base.BaseActivity
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH
import com.yyxnb.common_base.weight.NoScrollViewPager
import com.yyxnb.module_video.R
import com.yyxnb.module_video.databinding.ActivityVideoMainBinding
import com.yyxnb.module_video.ui.main.VideoMainBottomFragment
import com.yyxnb.module_video.ui.user.VideoUserFragment
import java.util.*

@Route(path = VIDEO_VIDEO)
class VideoActivity : BaseActivity() {
    //    @Override
    //    public Fragment initBaseFragment() {
    //        return new VideoMainFragment();
    //    }
    private var binding: ActivityVideoMainBinding? = null
    private var mViewPager: NoScrollViewPager? = null
    private var fragments: MutableList<Fragment>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_main)
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewPager = binding!!.mViewPager
        mViewPager!!.setNoScroll(true)
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(VideoMainBottomFragment())
            fragments?.add(VideoUserFragment.newInstance(false))
        }
        mViewPager!!.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
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

    override fun onBackPressed() {
        if (mViewPager != null && mViewPager!!.currentItem != 0) {
            mViewPager!!.setCurrentItem(0, true)
            return
        }
        super.onBackPressed()
    }
}