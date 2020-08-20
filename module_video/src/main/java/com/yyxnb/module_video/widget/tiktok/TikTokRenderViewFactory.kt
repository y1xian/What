package com.yyxnb.module_video.widget.tiktok

import android.content.Context
import com.dueeeke.videoplayer.render.IRenderView
import com.dueeeke.videoplayer.render.RenderViewFactory
import com.dueeeke.videoplayer.render.TextureRenderView

class TikTokRenderViewFactory : RenderViewFactory() {

    override fun createRenderView(context: Context): IRenderView {
        return TikTokRenderView(TextureRenderView(context))
    }

    companion object {
        fun create(): TikTokRenderViewFactory {
            return TikTokRenderViewFactory()
        }
    }
}