package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.FragmentVideoPlayBinding;
import com.yyxnb.module_video.viewmodel.VideoViewModel;

/**
 * 视频播放
 */
public class VideoPlayFragment extends BaseFragment {

    @BindViewModel
    VideoViewModel mViewModel;

    private FragmentVideoPlayBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initObservable() {
        mViewModel.reqVideoList();

        mViewModel.result.observe(this, data -> {

        });
    }
}