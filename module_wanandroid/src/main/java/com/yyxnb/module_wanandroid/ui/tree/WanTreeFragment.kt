package com.yyxnb.module_wanandroid.ui.tree

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.weight.ScaleTransitionPagerTitleView
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.databinding.FragmentWanTreeBinding
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * 广场.
 */
class WanTreeFragment : BaseFragment() {

    private var binding: FragmentWanTreeBinding? = null
    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private val titles = arrayOf("广场", "体系", "导航")
    private var fragments: MutableList<Fragment>? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_tree
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments!!.add(WanSquareFragment())
            fragments!!.add(WanSystemFragment())
            fragments!!.add(WanNavigationFragment())
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
                val colorTransitionPagerTitleView = ScaleTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.WHITE
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
}