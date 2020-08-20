package com.yyxnb.module_video.widget.tiktok

import android.graphics.Bitmap
import android.view.View
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.render.IRenderView

/**
 * TikTok专用RenderView，横屏视频默认显示，竖屏视频居中裁剪
 * 使用代理模式实现
 */
class TikTokRenderView internal constructor(private val mProxyRenderView: IRenderView) : IRenderView {

    override fun attachToPlayer(player: AbstractPlayer) {
        mProxyRenderView.attachToPlayer(player)
    }

    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
//        LogUtils.e(" setVideoSize : w " + videoWidth + " , h " +videoHeight);
        if (videoWidth > 0 && videoHeight > 0) {
            mProxyRenderView.setVideoSize(videoWidth, videoHeight)
            //            if (videoHeight > videoWidth) {
//                //竖屏视频，使用居中裁剪
//                mProxyRenderView.setScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
//            } else {
//                //横屏视频，使用默认模式
//                mProxyRenderView.setScaleType(VideoView.SCREEN_SCALE_DEFAULT);
//            }
        }
    }

    override fun setVideoRotation(degree: Int) {
        mProxyRenderView.setVideoRotation(degree)
    }

    override fun setScaleType(scaleType: Int) {
        // 置空，不要让外部去设置ScaleType
    }

    override fun getView(): View {
        return mProxyRenderView.view
    }

    override fun doScreenShot(): Bitmap {
        return mProxyRenderView.doScreenShot()
    }

    override fun release() {
        mProxyRenderView.release()
    }
}