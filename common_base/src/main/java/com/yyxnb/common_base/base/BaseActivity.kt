package com.yyxnb.common_base.base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.github.anzewei.parallaxbacklayout.ParallaxBack
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout
import com.yyxnb.arch.action.ArchAction
import com.yyxnb.arch.action.BundleAction
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.base.Java8Observer
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.common.action.AnimAction
import com.yyxnb.common.utils.KeyboardUtils.hideSoftInput
import com.yyxnb.skinloader.SkinInflaterFactory
import com.yyxnb.widget.action.ClickAction
import com.yyxnb.widget.action.HandlerAction
import me.jessyan.autosize.AutoSizeCompat
import java.lang.ref.WeakReference

/**
 * 建议 [ContainerActivity.initBaseFragment]  }
 */
@ParallaxBack(edgeMode = ParallaxBack.EdgeMode.EDGE)
abstract class BaseActivity : AppCompatActivity(), IActivity,
        ArchAction, BundleAction, HandlerAction, ClickAction, AnimAction {

    protected val TAG = javaClass.canonicalName
    protected var mContext: WeakReference<Context>? = null
    private val java8Observer: Java8Observer
    private val mActivityDelegate by lazy { getBaseDelegate() }

    override fun getContext(): Context {
        return mContext!!.get()!!
    }

    init {
        java8Observer = Java8Observer(TAG)
        lifecycle.addObserver(java8Observer)
        lifecycle.addObserver(mActivityDelegate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SkinInflaterFactory.setFactory(this)
        window.setBackgroundDrawable(null)
        mContext = WeakReference(this)
        super.onCreate(savedInstanceState)
    }

    override fun setSwipeBack(mSwipeBack: Int) {
        val layout = ParallaxHelper.getParallaxBackLayout(this, true)
        when (mSwipeBack) {
            SwipeStyle.FULL -> {
                ParallaxHelper.enableParallaxBack(this)
                //全屏滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL)
            }
            SwipeStyle.EDGE -> {
                ParallaxHelper.enableParallaxBack(this)
                //边缘滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT)
            }
            SwipeStyle.NONE -> ParallaxHelper.disableParallaxBack(this)
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityDelegate.onDestroy()
        lifecycle.removeObserver(java8Observer)
        mContext?.clear()
        mContext = null
    }

    @Nullable
    override fun getBundle(): Bundle? {
        return intent.extras
    }

    override fun getResources(): Resources {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        //如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        //如果有自定义需求就用这个方法
//        AutoSizeCompat.autoConvertDensity(super.getResources(), 667f, false);
        return super.getResources()
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        if (fragments.isEmpty()) {
            super.onBackPressed()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //把操作放在用户点击的时候
        if (event.action == MotionEvent.ACTION_DOWN) {
            //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            val v = currentFocus
            //判断用户点击的是否是输入框以外的区域
            if (mActivityDelegate.isShouldHideKeyboard(v, event)) {
                //收起键盘
                v?.let { hideSoftInput(it) }
            }
        }
        return super.onTouchEvent(event)
    }

    fun <T : IFragment> startFragment(targetFragment: T) {
        startFragment(targetFragment, 0)
    }

    fun <T : IFragment> startFragment(targetFragment: T, requestCode: Int) {
        try {
            val intent = Intent(this, ContainerActivity::class.java)
            val bundle = targetFragment.initArguments()
            bundle!!.putInt(ArchConfig.REQUEST_CODE, requestCode)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(ArchConfig.FRAGMENT, targetFragment.javaClass.getCanonicalName())
            intent.putExtra(ArchConfig.BUNDLE, bundle)
            startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T : IFragment> setRootFragment(fragment: T, containerId: Int) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(containerId, (fragment as Fragment), fragment.sceneId())
            transaction.addToBackStack(fragment.sceneId())
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}