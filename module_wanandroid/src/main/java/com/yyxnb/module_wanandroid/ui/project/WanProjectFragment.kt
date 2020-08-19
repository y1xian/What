package com.yyxnb.module_wanandroid.ui.project

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.weight.ScaleTransitionPagerTitleView
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanClassifyBean
import com.yyxnb.module_wanandroid.databinding.FragmentWanProjectBinding
import com.yyxnb.module_wanandroid.viewmodel.WanProjectViewModel
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * 项目.
 */
@BindRes
class WanProjectFragment : BaseFragment() {
    private var binding: FragmentWanProjectBinding? = null
    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null

    @BindViewModel
    lateinit var mViewModel: WanProjectViewModel
    private var titles: MutableList<String> = ArrayList()
    private var fragments: MutableList<Fragment> = ArrayList()

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_project
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
    }

    override fun initViewData() {}

    override fun initObservable() {
        mViewModel.getProjecTypes()
        mViewModel.projecTypes.observe(this, { data: List<WanClassifyBean>? ->
            if (data != null) {
                titles = ArrayList()
                fragments = ArrayList()
                titles.add("最新项目")
                fragments.add(WanProjectListFragment.newInstance(false, 0))
                for (classifyBean in data) {
                    titles.add(classifyBean.name)
                    fragments.add(WanProjectListFragment.newInstance(false, classifyBean.id))
                }
                initIndicator()
            }
        })
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.isAdjustMode = false
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ScaleTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.WHITE
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener { view: View? -> mViewPager!!.setCurrentItem(index, false) }
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
        mViewPager!!.offscreenPageLimit = titles.size
        mViewPager!!.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, titles)
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager)
    }
}