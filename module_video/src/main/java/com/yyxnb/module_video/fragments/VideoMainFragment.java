package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.module_video.R;

/**
 * 视频首页
 */
@BindRes
public class VideoMainFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}