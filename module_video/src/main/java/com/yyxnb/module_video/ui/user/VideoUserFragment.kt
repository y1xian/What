package com.yyxnb.module_video.ui.user

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.common.Bus.post
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH
import com.yyxnb.image_loader.utils.DrawableTintUtil
import com.yyxnb.module_video.R
import com.yyxnb.module_video.databinding.FragmentVideoUserBinding
import com.yyxnb.module_video.ui.VideoListFragment
import com.yyxnb.module_video.widget.AppBarStateListener
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
 * 视频 - 个人页面
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
class VideoUserFragment : BaseFragment() {

    private var binding: FragmentVideoUserBinding? = null
    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private var mAppBarLayout: AppBarLayout? = null
    private var mTitleLayout: RelativeLayout? = null
    private var mTitleName: TextView? = null
    private val titles = arrayOf("作品", "动态", "喜欢")
    private var fragments: MutableList<Fragment>? = null
    private val isUser = false
    private var mAppBarExpand = true //AppBarLayout是否展开
    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_user
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
        mAppBarLayout = binding!!.mAppBarLayout
        mTitleLayout = binding!!.mTitleLayout
        mTitleName = binding!!.mTitleName
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(VideoListFragment())
            fragments?.add(VideoListFragment())
            fragments?.add(VideoListFragment())
        }
        binding!!.btnBack.setOnClickListener { v: View? -> post(MsgEvent(KEY_VIDEO_BOTTOM_VP_SWITCH, "", 0)) }
        initIndicator()
    }

    override fun initObservable() {
        mAppBarLayout!!.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout: AppBarLayout, verticalOffset: Int ->
            val totalScrollRange = appBarLayout.totalScrollRange.toFloat()
            val rate = -1 * verticalOffset / totalScrollRange
            //                mTitle.setAlpha(rate);
            mTitleName!!.alpha = rate
            //            Drawable compat = DrawableCompat.wrap(binding.btnBack.getDrawable());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                DrawableCompat.setTintList(compat, ColorStateList.valueOf(Color.argb(rate,0,0,0)));
//            }
            Log.w("000000", "r : $rate")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (rate <= 0.2f) {
                    binding!!.btnBack.setImageDrawable(DrawableTintUtil.tintDrawable(binding!!.btnBack.drawable, Color.WHITE))
                } else {
                    binding!!.btnBack.setImageDrawable(DrawableTintUtil.tintListDrawable(binding!!.btnBack.drawable, ColorStateList.valueOf(Color.argb(rate, 0f, 0f, 0f))))
                }
            }
        })
        mAppBarLayout!!.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, state ->
            when (state) {
                AppBarStateListener.EXPANDED ->                         //L.e("#mAppBarLayout--------->展开");
                    mAppBarExpand = true
                AppBarStateListener.COLLAPSED ->                         //L.e("#mAppBarLayout--------->收起");
                    mAppBarExpand = false
            }
        })
    }

    override fun onVisible() {
//        isUser = getArguments().getBoolean("isUser", false);
//        binding.btnBack.setVisibility(isUser ? View.GONE : View.VISIBLE);
        post(MsgEvent(KEY_VIDEO_BOTTOM_VP, "", false))
        w("user v")
        //        if (!isUser && binding.btnBack.getVisibility() == View.VISIBLE){
//            monitor(true);
//        }else {
//            getView().setOnKeyListener(null);
//            monitor(false);
//        }
    }

    override fun onInVisible() {
        post(MsgEvent(KEY_VIDEO_BOTTOM_VP, "", true))
        w("user iv")
    }

    override fun onResume() {
        super.onResume()
    }

    private fun monitor(b: Boolean) {
        if (b) {
            requireView().isFocusableInTouchMode = true
            requireView().requestFocus()
            requireView().setOnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    post(MsgEvent(KEY_VIDEO_BOTTOM_VP_SWITCH, "", 0))
                    return@setOnKeyListener true
                }
                false
            }
        } else {
            requireView().isFocusableInTouchMode = false
            requireView().setOnKeyListener(null)
        }
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
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
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
                indicator.setColors(Color.BLACK)
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

    interface OnBackClickListener {
        fun onBackClick()
    }

    private var mOnBackClickListener: OnBackClickListener? = null
    fun setOnBackClickListener(listener: OnBackClickListener?) {
        mOnBackClickListener = listener
    }

    companion object {
        fun newInstance(isUser: Boolean): VideoUserFragment {
            val args = Bundle()
            args.putBoolean("isUser", isUser)
            val fragment = VideoUserFragment()
            fragment.arguments = args
            return fragment
        }
    }
}