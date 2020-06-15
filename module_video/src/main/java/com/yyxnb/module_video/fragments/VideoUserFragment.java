package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.module_video.R;

/**
 * 视频 - 个人页面
 */
@BindRes(subPage = true)
public class VideoUserFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_user;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void onVisible() {
        LogUtils.w("user v");
    }

    @Override
    public void onInVisible() {
        LogUtils.w("user iv");
    }
}