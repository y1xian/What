package com.yyxnb.common_base.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoScrollViewPager : ViewPager {

    private var noScroll = false
    private var animation = false

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    fun setAnimation(animation: Boolean) {
        this.animation = animation
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?) : super(context!!) {}

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onTouchEvent(arg0)
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onInterceptTouchEvent(arg0)
        }
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, animation)
    }
}