package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_video.R;

/**
 * 关注
 */
@BindRes(subPage = true)
public class VideoFollowFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_follow;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onVisible() {
        LogUtils.w("follow v");
    }

    @Override
    public void onInVisible() {
        LogUtils.w("follow iv");
    }
}