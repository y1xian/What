package com.yyxnb.arch.base

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import com.yyxnb.arch.interfaces.ILazyOwner

/**
 * Fragment快速实现懒加载的代理类
 *
 * @author yyx
 */
class LazyProxy(
        /**
         * 要操作的Fragment对象
         */
        private var mFragment: Fragment?) {
    /**
     * 沉浸式实现接口
     */
    private var mILazyOwner: ILazyOwner? = null
    /**
     * 是否第一次加载
     */
    private var mIsFirstVisible = true
    /**
     * 标志位，View已经初始化完成
     */
    private var isViewCreated = false
    /**
     * 可见状态
     */
    private var mCurrentVisibleState = false


    init {
        if (mFragment is ILazyOwner) {
            this.mILazyOwner = mFragment as ILazyOwner
        } else {
            throw IllegalArgumentException("Fragment请实现LazyOwner接口")
        }
    }

    /**
     * @param isVisibleToUser true 界面可见
     */
    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
        if (isViewCreated) {
            if (isVisibleToUser && !mCurrentVisibleState) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && mCurrentVisibleState) {
                dispatchUserVisibleHint(false)
            }
        }
    }

    fun onCreate(savedInstanceState: Bundle?) {
        mIsFirstVisible = true
    }

    fun onActivityCreated(savedInstanceState: Bundle?) {
        isViewCreated = true
        mILazyOwner?.initView(savedInstanceState)
        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
        if (!mFragment!!.isHidden && mFragment!!.userVisibleHint) {
            // 这里的限制只能限制 A - > B 两层嵌套
            dispatchUserVisibleHint(true)
        }
    }

    fun onResume() {
        if (!mIsFirstVisible) {
            if (!mFragment!!.isHidden && !mCurrentVisibleState && mFragment!!.userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }

    fun onPause() {
        // 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
        // 子 fragment 走到这里的时候自身又会调用一遍 ？
        if (mCurrentVisibleState && mFragment!!.userVisibleHint) {
            dispatchUserVisibleHint(false)
        }

    }

    fun onDestroy() {
        mFragment = null
        mILazyOwner = null
        mIsFirstVisible = true
        isViewCreated = false
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        if (mFragment!!.userVisibleHint) {
            mILazyOwner!!.onVisible()
        }
    }

    /**
     * @param hidden true 不在最前端显示
     */
    fun onHiddenChanged(hidden: Boolean) {
        dispatchUserVisibleHint(!hidden)
    }


    /**
     * 统一处理 显示隐藏
     *
     * @param visible
     */
    private fun dispatchUserVisibleHint(visible: Boolean) {
        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
        //但当父 fragment 不可见所以 mCurrentVisibleState = false 直接 return 掉
        //        LogUtils.e(getClass().getSimpleName() + "  dispatchUserVisibleHint isParentInvisible() " + isParentInvisible());
        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
        if (visible && isParentInvisible()) return

        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 mCurrentVisibleState 置位 false
        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
        if (mCurrentVisibleState == visible) {
            return
        }

        mCurrentVisibleState = visible

        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false
                mILazyOwner!!.initViewData()
            }
            if (isFragmentVisible(mFragment!!)) {
                mILazyOwner?.onVisible()
                dispatchChildVisibleState(true)
            }
        } else {
            dispatchChildVisibleState(false)
            mILazyOwner?.onInVisible()
        }
    }

    /**
     * 用于分发可见时间的时候父获取 fragment 是否隐藏
     *
     * @return true fragment 不可见， false 父 fragment 可见
     */
    private fun isParentInvisible(): Boolean {
        val fragment = mFragment!!.parentFragment as BaseFragment?
        return fragment != null && !isSupportVisible()

    }

    /**
     * 可见状态
     */
    private fun isSupportVisible(): Boolean {
        return mCurrentVisibleState
    }

    /**
     * 当前Fragment是否可见
     */
    private fun isFragmentVisible(fragment: Fragment): Boolean {
        return !fragment.isHidden && fragment.userVisibleHint
    }

    /**
     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
     *
     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
     *
     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
     *
     * @param visible
     */
    private fun dispatchChildVisibleState(visible: Boolean) {
        val childFragmentManager = mFragment!!.childFragmentManager
        val fragments = childFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            for (child in fragments) {
                if (child is BaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    dispatchUserVisibleHint(visible)
                }
            }
        }
    }

}