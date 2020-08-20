package com.yyxnb.module_video.ui.main

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.MESSAGE_LIST_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.USER_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterUtils.navFragment
import com.yyxnb.common_base.arouter.service.impl.LoginImpl.Companion.instance
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R
import com.yyxnb.module_video.databinding.FragmentVideoMainBottomBinding
import com.yyxnb.module_video.ui.find.VideoFindFragment
import com.yyxnb.module_video.ui.home.VideoHomeFragment
import com.yyxnb.view.text.DrawableRadioButton

/**
 * 主页
 */
@BindRes(subPage = true)
class VideoMainBottomFragment : BaseFragment(), View.OnClickListener {

    private var mBtnHome: DrawableRadioButton? = null
    private var mBtnFind: DrawableRadioButton? = null
    private var mBtnMsg: DrawableRadioButton? = null
    private var mBtnMe: DrawableRadioButton? = null
    private var mRecordTip: View? = null
    private var mAnimation: Animation? = null

    //当前选中的fragment的key
    private var mCurKey = 0
    private var mSparseArray: SparseArray<Fragment>? = null
    private var mFragmentManager: FragmentManager? = null
    private var mHomeFragment: VideoHomeFragment? = null
    private var mShowingRecordTip = false
    private var binding: FragmentVideoMainBottomBinding? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_main_bottom
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mBtnHome = binding!!.btnHome
        mBtnFind = binding!!.btnFind
        mBtnMsg = binding!!.btnMsg
        mBtnMe = binding!!.btnMe
        mRecordTip = binding!!.recordTip
        mBtnHome!!.setOnClickListener(this)
        mBtnFind!!.setOnClickListener(this)
        mBtnMsg!!.setOnClickListener(this)
        mBtnMe!!.setOnClickListener(this)
        mRecordTip?.setOnClickListener(this)
    }

    override fun initViewData() {
        e("main bottom initViewData ")
        mFragmentManager = childFragmentManager
        if (mSparseArray == null) {
            mSparseArray = SparseArray()
            mHomeFragment = VideoHomeFragment()
            mSparseArray!!.put(HOME, mHomeFragment)
            mSparseArray!!.put(FIND, VideoFindFragment())
            mSparseArray!!.put(MSG, navFragment(MESSAGE_LIST_FRAGMENT) as Fragment)
            //            mSparseArray.put(ME, VideoUserFragment.newInstance(true));
            mSparseArray!!.put(ME, navFragment(USER_FRAGMENT) as Fragment)
        }
        mCurKey = HOME
        val ft = mFragmentManager!!.beginTransaction()
        var i = 0
        val size = mSparseArray!!.size()
        while (i < size) {
            val fragment = mSparseArray!!.valueAt(i)
            ft.add(R.id.replaced, fragment)
            if (mSparseArray!!.keyAt(i) == mCurKey) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
            i++
        }
        ft.commit()
        mAnimation = TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, dp2px(context, 5f).toFloat())
        mAnimation?.setRepeatCount(-1)
        mAnimation?.setRepeatMode(Animation.REVERSE)
        mAnimation?.setDuration(400)
    }

    override fun onVisible() {
        e("main ov")
    }

    fun showRecordTip(show: Boolean) {
        if (mCurKey == ME) {
            toggleRecordTip(show)
        }
    }

    override fun onDestroy() {
        if (mRecordTip != null) {
            mRecordTip!!.clearAnimation()
        }
        super.onDestroy()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_home) {
            toggleHome()
        } else if (id == R.id.btn_find) {
            toggleFind()
        } else if (id == R.id.btn_msg) {
            toggleMsg()
        } else if (id == R.id.btn_me) {
            toggleMe()
        } else if (id == R.id.record_tip) {
            // 跳录制
        }
    }

    private fun toggle(key: Int) {
        if (key == mCurKey) {
            if (key == 0) {
                // 刷新首页
            }
            return
        }
        mCurKey = key
        val ft = mFragmentManager!!.beginTransaction()
        var i = 0
        val size = mSparseArray!!.size()
        while (i < size) {
            val fragment = mSparseArray!!.valueAt(i)
            if (mSparseArray!!.keyAt(i) == mCurKey) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
            i++
        }
        ft.commitAllowingStateLoss()
    }

    /**
     * 切换到Home
     */
    private fun toggleHome() {
        toggle(HOME)
        if (mBtnHome != null) {
            mBtnHome!!.doToggle()
        }
        if (mHomeFragment!!.isRecommend) {
            setCanScroll(true)
        }
        toggleRecordTip(false)
    }

    /**
     * 切换到发现
     */
    private fun toggleFind() {
        toggle(FIND)
        if (mBtnFind != null) {
            mBtnFind!!.doToggle()
        }
        setCanScroll(false)
        toggleRecordTip(false)
    }

    /**
     * 切换到消息
     */
    private fun toggleMsg() {
//        if (BaseConfig.getInstance().isLogin()) {
        toggle(MSG)
        if (mBtnMsg != null) {
            mBtnMsg!!.doToggle()
        }
        setCanScroll(false)
        toggleRecordTip(false)
        //        } else {
//            forwardLogin();
//        }
    }

    /**
     * 切换到我的
     */
    private fun toggleMe() {
//        if (BaseConfig.getInstance().isLogin()) {
        toggle(ME)
        if (mBtnMe != null) {
            mBtnMe!!.doToggle()
        }
        setCanScroll(false)
        toggleRecordTip(true)
        //        } else {
//            forwardLogin();
//        }
    }

    // 显示动画
    private fun toggleRecordTip(show: Boolean) {
        if (mShowingRecordTip == show) {
            return
        }
        mShowingRecordTip = show
        if (mRecordTip != null && mAnimation != null) {
            if (show) {
                if (mRecordTip!!.visibility != View.VISIBLE) {
                    mRecordTip!!.visibility = View.VISIBLE
                }
                mRecordTip!!.startAnimation(mAnimation)
            } else {
                mRecordTip!!.clearAnimation()
                if (mRecordTip!!.visibility == View.VISIBLE) {
                    mRecordTip!!.visibility = View.INVISIBLE
                }
            }
        }
    }

    // 跳转登录
    private fun forwardLogin() {
        e("跳登录 " + instance!!.userInfo.toString())
        startFragment(navFragment(LOGIN_FRAGMENT))
    }

    // 最底层vp { @link VideoMainFragment } 是否能切换 （视频/个人）
    private fun setCanScroll(b: Boolean) {}

    companion object {
        private const val HOME = 0
        private const val FIND = 1
        private const val MSG = 2
        private const val ME = 3
    }
}