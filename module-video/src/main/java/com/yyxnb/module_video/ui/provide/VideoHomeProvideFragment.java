package com.yyxnb.module_video.ui.provide;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_res.constants.VideoRouterPath;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.viewmodel.ViewModelFactory;
import com.yyxnb.module_video.ui.play.VideoPlayFragment;
import com.yyxnb.module_video.viewmodel.VideoViewModel;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/23
 * 历    史：
 * 描    述：对外提供的视频首页
 * ================================================
 */
@BindRes(subPage = true)
@Route(path = VideoRouterPath.SHOW_FRAGMENT)
public class VideoHomeProvideFragment extends VideoPlayFragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_video_home_provide, container, false);
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewModel = ViewModelFactory.createViewModel(this, VideoViewModel.class);
    }

    @Override
    public void initViewData() {
        super.initViewData();
    }

    @Override
    public void initObservable() {
        super.initObservable();

    }
}