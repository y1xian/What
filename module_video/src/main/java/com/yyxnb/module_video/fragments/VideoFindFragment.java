package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.module_video.R;

/**
 * 发现
 */
@BindRes(subPage = true)
public class VideoFindFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onVisible() {
        LogUtils.w("find v");
    }

    @Override
    public void onInVisible() {
        LogUtils.w("find iv");
    }

}