package com.yyxnb.module_wanandroid.ui.publicnumber

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.weight.ScaleTransitionPagerTitleView
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanClassifyBean
import com.yyxnb.module_wanandroid.databinding.FragmentWanPublicBinding
import com.yyxnb.module_wanandroid.viewmodel.WanPublicViewModel
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * 公众号.
 */
class WanPublicFragment : BaseFragment() {

    private var binding: FragmentWanPublicBinding? = null
    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private var titles: MutableList<String>? = null
    private var fragments: MutableList<Fragment>? = null

    @BindViewModel
    lateinit var mViewModel: WanPublicViewModel

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_public
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
    }

    override fun initViewData() {}
    override fun initObservable() {
        mViewModel.getPublicTypes()
        mViewModel.publicTypes.observe(this, { data: List<WanClassifyBean>? ->
            if (data != null) {
                titles = ArrayList()
                fragments = ArrayList()
                //                Collections.reverse(data);
                for (classifyBean in data) {
                    titles!!.add(classifyBean.name)
                    fragments!!.add(WanPublicListFragment.Companion.newInstance(classifyBean.id))
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
                return titles!!.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ScaleTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.WHITE
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.text = titles!![index]
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
        mViewPager!!.offscreenPageLimit = titles!!.size
        mViewPager!!.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, titles)
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager)
    }
}