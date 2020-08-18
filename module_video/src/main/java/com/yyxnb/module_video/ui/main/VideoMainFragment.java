package com.yyxnb.module_video.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.common.Bus;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.weight.NoScrollViewPager;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.FragmentVideoMainBinding;
import com.yyxnb.module_video.ui.user.VideoUserFragment;

import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.common_base.arouter.ARouterConstant.VIDEO_MAIN_FRAGMENT;
import static com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP;
import static com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH;

/**
 * 视频首页
 */
@BindRes
@Route(path = VIDEO_MAIN_FRAGMENT)
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
        mViewPager.setNoScroll(true);
    }

    @Override
    public void initViewData() {
        if (fragments == null){
            fragments = new ArrayList<>();
            fragments.add(new VideoMainBottomFragment());
            fragments.add(VideoUserFragment.newInstance(false));
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

    @Override
    public void initObservable() {
        Bus.observe(this,msgEvent -> {
            switch (msgEvent.code) {
                case KEY_VIDEO_BOTTOM_VP:
                    mViewPager.setNoScroll((Boolean) msgEvent.data);
                    break;
                case KEY_VIDEO_BOTTOM_VP_SWITCH:
                    mViewPager.setCurrentItem((Integer) msgEvent.data,true);
                    break;
            }
        });
    }

}