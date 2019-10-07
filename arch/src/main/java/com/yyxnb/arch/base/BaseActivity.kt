package com.yyxnb.arch.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.consumer.ActivitySlidingBackConsumer
import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.R
import com.yyxnb.arch.base.nav.FragmentHelper
import com.yyxnb.arch.base.nav.PresentAnimation
import com.yyxnb.arch.common.AppConfig
import com.yyxnb.arch.ext.hideKeyBoard
import com.yyxnb.arch.interfaces.*
import com.yyxnb.arch.jetpack.LifecycleDelegate
import com.yyxnb.arch.utils.ActivityManagerUtils
import com.yyxnb.arch.utils.MainThreadUtils
import com.yyxnb.arch.utils.StatusBarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


/**
 * Description: BaseActivity
 *
 * @author : yyx
 * @date : 2018/6/10
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    protected val TAG = javaClass.canonicalName

    protected lateinit var mContext: Context

//    protected var fragmentContainer: FrameLayout? = null

    private val lifecycleDelegate by lazy { LifecycleDelegate(this) }

    private var statusBarTranslucent = AppConfig.statusBarTranslucent
    private var fitsSystemWindows = AppConfig.fitsSystemWindows
    private var statusBarHidden = AppConfig.statusBarHidden
    private var statusBarDarkTheme = AppConfig.statusBarStyle
    private var navigationBarDarkTheme = AppConfig.navigationBarStyle
    private var swipeBack = AppConfig.swipeBackEnabled
    private var edgeSize = AppConfig.swipeEdgeSize

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        lifecycle.addObserver(Java8Observer(TAG))
        ActivityManagerUtils.pushActivity(this)
        initWindows()
        initAttributes()
        setContentView(initLayoutResId())
//        if (initLayoutResId() == 0) {
//            fragmentContainer = FrameLayout(this)
//            fragmentContainer!!.id = android.R.id.content
//            setContentView(fragmentContainer)
//        } else {
//            setContentView(initLayoutResId())
//        }
        initView(savedInstanceState)
    }

    //加载注解设置
    private fun initAttributes() {
        MainThreadUtils.post(Runnable {
            javaClass.getAnnotation(StatusBarTranslucent::class.java)?.let { statusBarTranslucent = it.value }
            javaClass.getAnnotation(StatusBarHidden::class.java)?.let { statusBarHidden = it.value }
            javaClass.getAnnotation(StatusBarDarkTheme::class.java)?.let { statusBarDarkTheme = it.value }
            javaClass.getAnnotation(SwipeBack::class.java)?.let { swipeBack = it.value }
            javaClass.getAnnotation(SwipeEdgeSize::class.java)?.let { edgeSize = it.value }
            javaClass.getAnnotation(FitsSystemWindows::class.java)?.let { fitsSystemWindows = it.value }

            setStatusBarTranslucent(statusBarTranslucent, fitsSystemWindows)
            setStatusBarStyle(statusBarDarkTheme)
            setNavigationBarStyle(navigationBarDarkTheme)
            setStatusBarHidden(statusBarHidden)
            setSwipeBack(swipeBack, edgeSize)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManagerUtils.deleteActivity(this)
        cancel() // 关闭页面后，结束所有协程任务
//        overridePendingTransition(com.yyxnb.arch.R.anim.slide_in_right, com.yyxnb.arch.R.anim.slide_out_left)
    }

    /**
     * 初始化窗口, 在界面未初始化之前调用的初始化操作
     */
    open fun initWindows() {}

    /**
     * 初始化根布局
     */
    @LayoutRes
    abstract fun initLayoutResId(): Int

    /**
     * 初始化
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化复杂数据 懒加载
     */
//    open fun initViewData() {}

    //开启沉浸式
    fun setStatusBarTranslucent(translucent: Boolean, fitsSystemWindows: Boolean) {
        StatusBarUtils.setStatusBarTranslucent(window, translucent, fitsSystemWindows)
    }

    //状态栏颜色
    fun setStatusBarColor(color: Int) {
        var mColor = color
        //判断是否Color类下
        if (mColor > 0) {
            mColor = resources.getColor(mColor)
        }
        StatusBarUtils.setStatusBarColor(window, mColor, AppConfig.animated)
    }

    //状态栏字体
    fun setStatusBarStyle(barStyle: BarStyle) {
        StatusBarUtils.setStatusBarStyle(window, barStyle === BarStyle.DarkContent)
    }

    //隐藏状态栏
    fun setStatusBarHidden(hidden: Boolean) {
        StatusBarUtils.setStatusBarHidden(window, hidden)
    }

    //隐藏状态栏
    fun setSwipeBack(mSwipeBack: Boolean = swipeBack, mEdgeSize: Int = edgeSize) {
        if (mSwipeBack) {
            SmartSwipe.wrap(this)
//                    .addConsumer(SpaceConsumer())
//                    .enableVertical() //工作方向：纵向
                    .addConsumer(ActivitySlidingBackConsumer(this))
                    //设置联动系数
                    .setRelativeMoveFactor(0.5F)
                    //侧滑边距 0为全屏滑动
                    .setEdgeSize(SmartSwipe.dp2px(mEdgeSize, this))
                    //指定可侧滑返回的方向，如：enableLeft() 仅左侧可侧滑返回
                    .enableLeft()
        }

    }

    //底部栏颜色
    fun setNavigationBarColor(color: Int) {
        StatusBarUtils.setNavigationBarColor(window, color)
    }

    //底部栏字体
    fun setNavigationBarStyle(barStyle: BarStyle) {
        StatusBarUtils.setNavigationBarStyle(window, barStyle === BarStyle.DarkContent)
    }

    //隐藏虚拟导航栏
    fun setNavigationBarHidden(hidden: Boolean) {
        StatusBarUtils.setNavigationBarHidden(window, hidden)
    }

//    override fun onBackPressed() {
//        val fragments = supportFragmentManager.fragments
//        if (fragments.size > 0) {
//            ActivityCompat.finishAfterTransition(this)
//        } else {
//            super.onBackPressed()
//        }
//    }

    fun startFragment(targetFragment: BaseFragment) {
        scheduleTaskAtStarted(Runnable {
            val intent = Intent(this, ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(AppConfig.FRAGMENT, targetFragment.javaClass.canonicalName)
            intent.putExtra(AppConfig.BUNDLE, targetFragment.initArguments())
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        })
    }

    fun setRootFragment(fragment: BaseFragment, containerId: Int = R.id.content) {
        scheduleTaskAtStarted(Runnable {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(containerId, fragment, fragment.getSceneId())
            transaction.addToBackStack(fragment.getSceneId())
            transaction.commit()
        })

    }


    @JvmOverloads
    protected fun scheduleTaskAtStarted(runnable: Runnable, interval: Long = 1L) {
        lifecycleDelegate.scheduleTaskAtStarted(runnable, interval)
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //把操作放在用户点击的时候
        if (event.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, event)) { //判断用户点击的是否是输入框以外的区域
                //收起键盘
                v.hideKeyBoard()
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {  //判断得到的焦点控件是否包含EditText
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            //得到输入框在屏幕中上下左右的位置
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略
        return false
    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val count = fragmentManager.backStackEntryCount
        if (count > 0) {
            val entry = fragmentManager.getBackStackEntryAt(count - 1)
            val fragment = fragmentManager.findFragmentByTag(entry.name) as BaseFragment?
            if (fragment != null && fragment.isAdded && !fragment.dispatchBackPressed()) {
                if (count == 1) {
                    if (!handleBackPressed()) {
                        ActivityCompat.finishAfterTransition(this)
                    }
                } else {
                    dismissFragment(fragment)
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    protected fun handleBackPressed(): Boolean {
        return false
    }

    fun presentFragment(fragment: BaseFragment) {
        scheduleTaskAtStarted(Runnable { presentFragmentInternal(fragment) })
    }

    private fun presentFragmentInternal(fragment: BaseFragment) {
        FragmentHelper.addFragmentToBackStack(supportFragmentManager, android.R.id.content, fragment, PresentAnimation.Push)
    }

    fun dismissFragment(fragment: BaseFragment) {
        scheduleTaskAtStarted(Runnable { dismissFragmentInternal(fragment) })
    }

    private fun dismissFragmentInternal(fragment: BaseFragment) {
        if (!fragment.isAdded) {
            return
        }
        val fragmentManager = supportFragmentManager
        FragmentHelper.executePendingTransactionsSafe(fragmentManager)

        val topFragment = fragmentManager.findFragmentById(android.R.id.content) as BaseFragment?
                ?: return
        topFragment.setAnimation(PresentAnimation.Push)
        val presented = getPresentedFragment(fragment)
        if (presented != null) {
            fragment.setAnimation(PresentAnimation.Push)
            topFragment.userVisibleHint = false
            topFragment.onHiddenChanged(true)
            supportFragmentManager.popBackStack(presented.getSceneId(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
            FragmentHelper.executePendingTransactionsSafe(supportFragmentManager)
//            fragment.onFragmentResult(topFragment.getRequestCode(), topFragment.getResultCode(), topFragment.getResultData())
        } else {
            val presenting = getPresentingFragment(fragment)
            presenting?.setAnimation(PresentAnimation.Push)
            fragment.userVisibleHint = false
            if (presenting == null) {
                ActivityCompat.finishAfterTransition(this)
            } else {
                fragmentManager.popBackStack(fragment.getSceneId(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                FragmentHelper.executePendingTransactionsSafe(fragmentManager)
//                presenting.onFragmentResult(fragment.getRequestCode(), fragment.getResultCode(), fragment.getResultData())
            }
        }
    }

    fun getPresentedFragment(fragment: BaseFragment): BaseFragment? {
        return FragmentHelper.getLatterFragment(supportFragmentManager, fragment)
    }

    fun getPresentingFragment(fragment: BaseFragment): BaseFragment? {
        return FragmentHelper.getAheadFragment(supportFragmentManager, fragment)
    }

    @JvmOverloads
    fun startActivityRootFragment(rootFragment: BaseFragment, containerId: Int = android.R.id.content) {
        scheduleTaskAtStarted(Runnable { setRootFragmentInternal(rootFragment, containerId) })
    }

    private fun setRootFragmentInternal(fragment: BaseFragment, containerId: Int) {
        val fragmentManager = supportFragmentManager
        val count = fragmentManager.backStackEntryCount
        if (count > 0) {
            val tag = fragmentManager.getBackStackEntryAt(0).name
            val former = fragmentManager.findFragmentByTag(tag) as BaseFragment?
            if (former != null && former.isAdded) {
                former.setAnimation(PresentAnimation.Push)
                fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                hasFormerRoot = true
            }
        }

        val transaction = fragmentManager.beginTransaction()
//        transaction.setReorderingAllowed(true)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragment.setAnimation(PresentAnimation.Push)
        transaction.add(containerId, fragment, fragment.getSceneId())
        transaction.addToBackStack(fragment.getSceneId())
        transaction.commit()
    }
}
