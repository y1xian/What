package com.yyxnb.module_video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import static com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO;

@Route(path = VIDEO_VIDEO)
public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }
}