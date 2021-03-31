package com.yyxnb.module_video.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.VideoRouterPath;
import com.yyxnb.common_res.weight.NoScrollViewPager;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.FragmentVideoMainBinding;
import com.yyxnb.module_video.ui.user.VideoUserFragment;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.helper.BusHelper;

import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP;
import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH;

/**
 * 视频首页
 */
@BindRes
@Route(path = VideoRouterPath.MAIN_FRAGMENT)
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
        mViewPager = binding.vpContent;
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
        BusHelper.observe(this, msgEvent -> {
            switch (msgEvent.getCode()) {
                case KEY_VIDEO_BOTTOM_VP:
                    mViewPager.setNoScroll((Boolean) msgEvent.getData());
                    break;
                case KEY_VIDEO_BOTTOM_VP_SWITCH:
                    mViewPager.setCurrentItem((Integer) msgEvent.getData(),true);
                    break;
            }
        });
    }

}