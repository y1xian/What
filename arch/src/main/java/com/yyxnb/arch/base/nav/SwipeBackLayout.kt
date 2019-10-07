package com.yyxnb.arch.base.nav

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yyxnb.arch.R

/**
 * modified from https://github.com/ikew0ng/SwipeBackLayout
 */
class SwipeBackLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs) {

    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private var mScrollThreshold = DEFAULT_SCROLL_THRESHOLD

    private var mParallaxOffset = DEFAULT_PARALLAX

    private var mCapturedView: View? = null

    private val mDragHelper: ViewDragHelper

    private var mScrollPercent: Float = 0.toFloat()

    private var mLeft: Int = 0

    /**
     * The set of listeners to be sent events through.
     */
    private var mListener: SwipeListener? = null

    private val mShadowLeft: Drawable?

    private var mTabBar: Drawable? = null

    private var mTabBarOriginBounds: Rect? = null

    private var mShadowOpacity: Float = 0.toFloat()

    private var mInLayout: Boolean = false

    private val mTmpRect = Rect()

    init {
        mShadowLeft = ContextCompat.getDrawable(context, R.drawable.nav_shadow_left)

        mDragHelper = ViewDragHelper.create(this, ViewDragCallback())
        val density = resources.displayMetrics.density
        val minVelocity = MIN_FLING_VELOCITY * density
        mDragHelper.minVelocity = minVelocity
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    fun setTabBar(drawable: Drawable?) {
        this.mTabBar = drawable
        if (drawable != null) {
            mTabBarOriginBounds = drawable.copyBounds()
        }
    }

    fun setParallaxOffset(offset: Float) {
        mParallaxOffset = offset
    }

    /**
     * Add a callback to be invoked when a swipe event is sent to this view.
     *
     * @param listener the swipe listener to attach to this view
     */
    fun setSwipeListener(listener: SwipeListener) {
        mListener = listener
    }

    interface SwipeListener {

        fun onViewDragStateChanged(state: Int, scrollPercent: Float)

        fun shouldSwipeBack(): Boolean
    }

    fun setScrollThresHold(threshold: Float) {
        if (threshold >= 1.0f || threshold <= 0) {
            throw IllegalArgumentException("Threshold value should be between 0 and 1.0")
        }
        mScrollThreshold = threshold
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!mListener!!.shouldSwipeBack()) {
            return super.onInterceptTouchEvent(event)
        }

        try {
            return mDragHelper.shouldInterceptTouchEvent(event)
        } catch (e: ArrayIndexOutOfBoundsException) {
            return false
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mListener!!.shouldSwipeBack()) {
            return super.onTouchEvent(event)
        }
        mDragHelper.processTouchEvent(event)
        return mDragHelper.viewDragState != ViewDragHelper.STATE_IDLE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mInLayout = true
        if (mCapturedView != null)
            mCapturedView!!.layout(mLeft, 0,
                    mLeft + mCapturedView!!.measuredWidth,
                    mCapturedView!!.measuredHeight)
        mInLayout = false
    }

    override fun requestLayout() {
        if (!mInLayout) {
            super.requestLayout()
        }
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        val drawContent = child === mCapturedView
        val index = indexOfChild(child)
        if (mDragHelper.viewDragState != ViewDragHelper.STATE_IDLE && index == childCount - 2) {
            val lastChild = getChildAt(childCount - 1)
            canvas.save()
            canvas.clipRect(0, 0, lastChild.left, height)
        }

        val ret = super.drawChild(canvas, child, drawingTime)

        if (mDragHelper.viewDragState != ViewDragHelper.STATE_IDLE && index == childCount - 2) {
            canvas.restore()
        }

        if (mTabBar != null && drawContent) {
            drawTabBar(canvas, child)
        }

        if (mShadowOpacity > 0 && drawContent
                && mDragHelper.viewDragState != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child)
        }
        return ret
    }

    private fun drawShadow(canvas: Canvas, child: View) {
        val childRect = mTmpRect
        child.getHitRect(childRect)
        mShadowLeft!!.setBounds(childRect.left - mShadowLeft.intrinsicWidth, childRect.top,
                childRect.left, childRect.bottom)
        mShadowLeft.alpha = (mShadowOpacity * FULL_ALPHA).toInt()
        mShadowLeft.draw(canvas)
    }

    private fun drawTabBar(canvas: Canvas, child: View) {
        canvas.save()
        canvas.clipRect(0, 0, child.left, height)
        val leftOffset = ((mCapturedView!!.left - width).toFloat() * mParallaxOffset * mShadowOpacity).toInt()
        mTabBar!!.setBounds(leftOffset, mTabBarOriginBounds!!.top, mTabBarOriginBounds!!.right + leftOffset, mTabBarOriginBounds!!.bottom)
        mTabBar!!.draw(canvas)
        canvas.restore()
    }

    override fun computeScroll() {
        mShadowOpacity = 1 - mScrollPercent
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }

        val count = childCount
        if (mShadowOpacity >= 0 && mCapturedView != null && count > 1) {
            val leftOffset = ((mCapturedView!!.left - width).toFloat() * mParallaxOffset * mShadowOpacity).toInt()
            val underlying = getChildAt(count - 2)
            underlying.x = (if (leftOffset > 0) 0 else leftOffset).toFloat()
        }
    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(view: View, pointerId: Int): Boolean {
            if (view.animation != null) {
                return false
            }

            val ret = mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId)
            val directionCheck = !mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL, pointerId)
            return mDragHelper.viewDragState != ViewDragHelper.STATE_SETTLING && ret and directionCheck
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            mCapturedView = capturedChild
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return child.width
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            mScrollPercent = Math.abs(left.toFloat() / mCapturedView!!.width)
            mLeft = left
            invalidate()
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val childWidth = releasedChild.width
            val left = if (xvel > 0 || xvel == 0f && mScrollPercent > mScrollThreshold) childWidth else 0
            mDragHelper.settleCapturedViewAt(left, 0)
            invalidate()
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return Math.min(child.width, Math.max(left, 0))
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                mCapturedView = null
                mLeft = 0
                val count = childCount
                if (count > 1) {
                    val underlying = getChildAt(count - 2)
                    underlying.x = 0f
                }
            }
            mListener!!.onViewDragStateChanged(state, mScrollPercent)
        }
    }

    companion object {

        private val TAG = "SwipeBackLayout"

        /**
         * Minimum velocity that will be detected as a fling
         */
        private val MIN_FLING_VELOCITY = 400 // dips per second

        private val FULL_ALPHA = 255

        /**
         * A view is not currently being dragged or animating as a result of a
         * fling/snap.
         */
        @JvmField
        val STATE_IDLE = ViewDragHelper.STATE_IDLE

        /**
         * A view is currently being dragged. The position is currently changing as
         * a result of user input or simulated user input.
         */
        @JvmField
        val STATE_DRAGGING = ViewDragHelper.STATE_DRAGGING

        /**
         * A view is currently settling into place as a result of a fling or
         * predefined non-interactive motion.
         */
        val STATE_SETTLING = ViewDragHelper.STATE_SETTLING

        /**
         * Default threshold of scroll
         */
        private val DEFAULT_SCROLL_THRESHOLD = 0.4f

        private val DEFAULT_PARALLAX = 0.3f
    }
}
