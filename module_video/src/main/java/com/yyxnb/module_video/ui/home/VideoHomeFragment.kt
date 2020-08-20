package com.yyxnb.module_video.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R
import com.yyxnb.module_video.databinding.FragmentVideoHomeBinding
import com.yyxnb.module_video.ui.play.VideoPlayFragment
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

/**
 * 首页
 */
@BindRes(subPage = true)
class VideoHomeFragment : BaseFragment() {

    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private val titles = arrayOf("关注", "推荐")
    private var fragments: MutableList<Fragment>? = null
    private var binding: FragmentVideoHomeBinding? = null
    private val mCurKey = 0

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(VideoFollowFragment())
            fragments?.add(VideoPlayFragment())
        }
        initIndicator()
        mIndicator!!.onPageSelected(1)
        mViewPager!!.currentItem = 1
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener { view: View? -> mViewPager!!.currentItem = index }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                //设置宽度
                indicator.lineWidth = dp2px(getContext(), 20f).toFloat()
                //设置高度
                indicator.lineHeight = dp2px(getContext(), 2f).toFloat()
                //设置颜色
                indicator.setColors(Color.WHITE)
                //设置圆角
                indicator.roundRadius = 5f
                //设置模式
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                return indicator
            }
        }
        mIndicator!!.navigator = commonNavigator
        mViewPager!!.offscreenPageLimit = titles.size - 1
        mViewPager!!.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, Arrays.asList(*titles))
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager)
    }

    val isRecommend: Boolean
        get() = mCurKey == RECOMMEND

    companion object {
        private const val RECOMMEND = 0
        private const val FOLLOW = 1
    }
}