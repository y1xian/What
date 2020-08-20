package com.yyxnb.module_video.widget.tiktok

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.L
import com.yyxnb.module_video.R

class TikTokView : FrameLayout, IControlComponent {
    private var thumb: ImageView? = null
    private var mPlayBtn: ImageView? = null
    private var mControlWrapper: ControlWrapper? = null
    private var mScaledTouchSlop = 0
    private var mStartX = 0
    private var mStartY = 0

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    /**
     * 解决点击和VerticalViewPager滑动冲突问题
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                return true
            }
            MotionEvent.ACTION_UP -> {
                val endX = event.x.toInt()
                val endY = event.y.toInt()
                if (Math.abs(endX - mStartX) < mScaledTouchSlop
                        && Math.abs(endY - mStartY) < mScaledTouchSlop) {
                    performClick()
                }
            }
        }
        return false
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View {
        return this
    }

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation) {}
    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE -> {
                L.e("STATE_IDLE " + hashCode())
                thumb!!.visibility = VISIBLE
            }
            VideoView.STATE_PLAYING -> {
                L.e("STATE_PLAYING " + hashCode())
                thumb!!.visibility = GONE
                mPlayBtn!!.visibility = GONE
            }
            VideoView.STATE_PAUSED -> {
                L.e("STATE_PAUSED " + hashCode())
                thumb!!.visibility = GONE
                mPlayBtn!!.visibility = VISIBLE
            }
            VideoView.STATE_PREPARED -> L.e("STATE_PREPARED " + hashCode())
            VideoView.STATE_ERROR -> {
                L.e("STATE_ERROR " + hashCode())
                Toast.makeText(context, R.string.dkplayer_error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {}
    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLocked: Boolean) {}

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_tiktok_controller, this, true)
        thumb = findViewById(R.id.iv_thumb)
        mPlayBtn = findViewById(R.id.play_btn)
        setOnClickListener { mControlWrapper!!.togglePlay() }
        mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }
}