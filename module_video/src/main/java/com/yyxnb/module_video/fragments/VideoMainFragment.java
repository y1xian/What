package com.yyxnb.module_video.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.module_base.weight.NoScrollViewPager;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.FragmentVideoMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频首页
 */
@BindRes
public class VideoMainFragment extends BaseFragment {

    private FragmentVideoMainBinding binding;
    private NoScrollViewPager mViewPager;

    private List<Fragment> fragments;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mViewPager = binding.mViewPager;
    }

    @Override
    public void initViewData() {
        if (fragments == null){
            fragments = new ArrayList<>();
            fragments.add(new VideoMainBottomFragment());
            fragments.add(new VideoUserFragment());
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }
}