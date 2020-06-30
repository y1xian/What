package com.yyxnb.module_video.fragments;

import android.os.Bundle;

import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_video.R;

/**
 * 消息
 */
@BindRes(subPage = true)
public class VideoMessageFragment extends BaseFragment {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void onVisible() {
        LogUtils.w("msg v");
    }

    @Override
    public void onInVisible() {
        LogUtils.w("msg iv");
    }
}