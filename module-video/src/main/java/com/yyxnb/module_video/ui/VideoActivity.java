package com.yyxnb.module_video.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseActivity;
import com.yyxnb.common_res.constants.VideoRouterPath;
import com.yyxnb.common_res.weight.NoScrollViewPager;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.ActivityVideoMainBinding;
import com.yyxnb.module_video.ui.main.VideoMainBottomFragment;
import com.yyxnb.module_video.ui.user.VideoUserFragment;
import com.yyxnb.what.arch.helper.BusHelper;

import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP;
import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH;

@Route(path = VideoRouterPath.MAIN_ACTIVITY)
public class VideoActivity extends BaseActivity {

//    @Override
//    public Fragment initBaseFragment() {
//        return new VideoMainFragment();
//    }

    private ActivityVideoMainBinding binding;

    private NoScrollViewPager mViewPager;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewPager = binding.vpContent;
        mViewPager.setNoScroll(true);
    }

    @Override
    public void initViewData() {
        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new VideoMainBottomFragment());
            fragments.add(VideoUserFragment.newInstance(false));
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                    mViewPager.setCurrentItem((Integer) msgEvent.getData(), true);
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0, true);
            return;
        }
        super.onBackPressed();
    }
}