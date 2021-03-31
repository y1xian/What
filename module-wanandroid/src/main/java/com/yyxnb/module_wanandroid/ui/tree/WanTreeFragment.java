package com.yyxnb.module_wanandroid.ui.tree;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.weight.ScaleTransitionPagerTitleView;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.databinding.FragmentWanTreeBinding;
import com.yyxnb.what.adapter.base.BaseFragmentPagerAdapter;
import com.yyxnb.what.core.DpUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 广场.
 */
public class WanTreeFragment extends BaseFragment {

    private FragmentWanTreeBinding binding;

    private MagicIndicator mIndicator;
    private ViewPager mViewPager;
    private String[] titles = {"广场", "体系", "导航"};
    private List<Fragment> fragments;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_tree;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mIndicator = binding.vIndicator;
        mViewPager = binding.vpContent;
    }

    @Override
    public void initViewData() {
        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new WanSquareFragment());
            fragments.add(new WanSystemFragment());
            fragments.add(new WanNavigationFragment());
        }

        initIndicator();
        mIndicator.onPageSelected(1);
        mViewPager.setCurrentItem(1);
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.WHITE);
                colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
                colorTransitionPagerTitleView.setText(titles[index]);
                colorTransitionPagerTitleView.setOnClickListener(view -> mViewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                //设置宽度
                indicator.setLineWidth(DpUtils.dp2px(getContext(), 20));
                //设置高度
                indicator.setLineHeight(DpUtils.dp2px(getContext(), 2));
                //设置颜色
                indicator.setColors(Color.WHITE);
                //设置圆角
                indicator.setRoundRadius(5);
                //设置模式
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);

        mViewPager.setOffscreenPageLimit(titles.length - 1);
        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(titles)));
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

}