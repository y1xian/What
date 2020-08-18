package com.yyxnb.arch.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.delegate.ActivityDelegate
import com.yyxnb.arch.delegate.FragmentDelegate
import java.util.*

/**
 * 堆栈式管理
 */
object AppManager {

    @Volatile
    var activityStack: Stack<Activity>? = null

    @Volatile
    var fragmentStack: Stack<Fragment>? = null

    @Volatile
    var activityDelegates: LinkedHashMap<Int, ActivityDelegate>? = null
        get() {
            if (field == null) {
                field = LinkedHashMap()
            }
            return field
        }

    @Volatile
    var fragmentDelegates: LinkedHashMap<Int, FragmentDelegate>? = null
        get() {
            if (field == null) {
                field = LinkedHashMap()
            }
            return field
        }

    fun getActivityDelegate(iActivity: IActivity?, hasCode: Int): ActivityDelegate {
        var delegate = activityDelegates!![hasCode]
        if (delegate == null) {
            delegate = ActivityDelegate(iActivity)
            activityDelegates!![hasCode] = delegate
        }
        return delegate
    }

    fun getFragmentDelegate(iFragment: IFragment?, hasCode: Int): FragmentDelegate {
        var delegate = fragmentDelegates!![hasCode]
        if (delegate == null) {
            delegate = FragmentDelegate(iFragment)
            fragmentDelegates!![hasCode] = delegate
        }
        return delegate
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    /**
     * 是否有activity
     */
    val isActivity: Boolean
        get() = if (activityStack != null) {
            !activityStack!!.isEmpty()
        } else false

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return activityStack!!.lastElement()
    }
    /**
     * 结束指定的Activity
     */
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    @JvmOverloads
    fun finishActivity(activity: Activity? = currentActivity()) {
        if (activity != null) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                finishActivity(activityStack!![i])
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null) {
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        }
        return null
    }

    /**
     * 添加Fragment到堆栈
     */
    fun addFragment(fragment: Fragment) {
        if (fragmentStack == null) {
            fragmentStack = Stack()
        }
        fragmentStack!!.add(fragment)
    }

    /**
     * 移除指定的Fragment
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            fragmentStack!!.remove(fragment)
        }
    }

    /**
     * 是否有Fragment
     */
    val isFragment: Boolean
        get() = if (fragmentStack != null) {
            !fragmentStack!!.isEmpty()
        } else false

    /**
     * 获取当前Fragment（堆栈中最后一个压入的）
     */
    fun currentFragment(): Fragment? {
        return if (fragmentStack != null) {
            fragmentStack!!.lastElement()
        } else null
    }

    /**
     * 退出应用程序
     */
    fun exit() {
        try {
            finishAllActivity()
            // 杀死该应用进程
//          android.os.Process.killProcess(android.os.Process.myPid());
//            调用 System.exit(n) 实际上等效于调用：
//            Runtime.getRuntime().exit(n)
//            finish()是Activity的类方法，仅仅针对Activity，当调用finish()时，只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；
//            当调用System.exit(0)时，退出当前Activity并释放资源（内存），但是该方法不可以结束整个App如有多个Activty或者有其他组件service等不会结束。
//            其实android的机制决定了用户无法完全退出应用，当你的application最长时间没有被用过的时候，android自身会决定将application关闭了。
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            activityStack!!.clear()
            fragmentStack!!.clear()
            activityDelegates!!.clear()
            fragmentDelegates!!.clear()
            activityStack = null
            fragmentStack = null
            activityDelegates = null
            fragmentDelegates = null
        }
    }

    /**
     * 退回桌面进入后台
     */
    fun returnDesktop(activity: AppCompatActivity) {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(homeIntent)
    }

}