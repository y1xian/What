package com.yyxnb.module_video

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.alibaba.android.arouter.facade.annotation.Route

import com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO
import com.yyxnb.module_video.fragments.VideoMainFragment

@Route(path = VIDEO_VIDEO)
class VideoActivity : AppCompatActivity() {

    fun getContainerViewId():Int = R.id.atyContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }
}
