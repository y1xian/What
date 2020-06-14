package com.yyxnb.module_video;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.ContainerActivity;
import com.yyxnb.module_video.fragments.VideoPlayFragment;

import static com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO;

@Route(path = VIDEO_VIDEO)
public class VideoActivity extends ContainerActivity {

    @Override
    public Fragment initBaseFragment() {
//        return new VideoMainFragment();
        return new VideoPlayFragment();
    }

}