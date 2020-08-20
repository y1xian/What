package com.yyxnb.module_video.widget.tiktok

import android.content.Context
import android.util.AttributeSet
import com.dueeeke.videoplayer.controller.BaseVideoController

/**
 * 抖音
 */
class TikTokController : BaseVideoController {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun getLayoutId(): Int {
        return 0
    }

    override fun showNetWarning(): Boolean {
        //不显示移动网络播放警告
        return false
    }
}