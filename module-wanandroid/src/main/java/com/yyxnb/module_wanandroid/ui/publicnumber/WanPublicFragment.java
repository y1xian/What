package com.yyxnb.module_wanandroid.ui.publicnumber;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.weight.ScaleTransitionPagerTitleView;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.databinding.FragmentWanPublicBinding;
import com.yyxnb.module_wanandroid.viewmodel.WanPublicViewModel;
import com.yyxnb.what.adapter.base.BaseFragmentPagerAdapter;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.core.DpUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 公众号.
 */
public class WanPublicFragment extends BaseFragment {

    private FragmentWanPublicBinding binding;

    private MagicIndicator mIndicator;
    private ViewPager mViewPager;

    private List<String> titles;
    private List<Fragment> fragments;

    @BindViewModel
    WanPublicViewModel mViewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_public;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mIndicator = binding.vIndicator;
        mViewPager = binding.vpContent;

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initObservable() {
        mViewModel.getPublicTypes();

        mViewModel.publicTypes.observe(this, data -> {
            if (data != null) {

                titles = new ArrayList<>();
                fragments = new ArrayList<>();
//                Collections.reverse(data);
                for (WanClassifyBean classifyBean : data){
                    titles.add(classifyBean.name);
                    fragments.add(WanPublicListFragment.newInstance(classifyBean.id));
                }

                initIndicator();
            }
        });
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.WHITE);
                colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
                colorTransitionPagerTitleView.setText(titles.get(index));
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

        mViewPager.setOffscreenPageLimit(titles.size());
        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, titles));
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }
}