package com.yyxnb.arch.base

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yyxnb.arch.base.nav.*

import com.yyxnb.arch.utils.MainThreadUtils

import java.util.ArrayList

/**
 * 根activity
 *
 * Created by panda on 2017/7/24.
 */
abstract class RootActivity : AppCompatActivity() {
    lateinit var stackManager: OpsManager

    /**
     * 当前附着在activity上的顶层fragment
     *
     * @return
     */
    val curAttachedFragment: BaseFragment
        get() {
            val fragmentsCount = supportFragmentManager.backStackEntryCount
            val fragments = supportFragmentManager.fragments
            val realFragments = ArrayList<Fragment>()
            for (fragment in fragments) {
                if (fragment != null) {
                    realFragments.add(fragment)
                }
            }
            return realFragments[fragmentsCount] as BaseFragment
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var rootView = contentView()
        val fragmentView = fragmentContainer()
        if (rootView == null) {
            rootView = fragmentView
        }
        setContentView(rootView)

        stackManager = OpsManager(this)

        val bundle = intent.extras
        if (bundle != null) {
            // 控制是否需要加载基本的base fragment，默认是需要加载的
            // 在从其他activity跳转到另一个activity的fragment时可能不需要
            val newFragment = bundle.getString(NEW_FRAGMENT_KEY, "")
            if (newFragment.isEmpty()) {
                val fragment = baseFragment()
                if (fragment != null) {
                    stackManager.replace(fragment)
                }
            } else {
                try {
                    val fragmentClass = Class.forName(newFragment)
                    val fragment = fragmentClass.newInstance() as BaseFragment
                    stackManager.replace(fragment, bundle)
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }

//            mAnimBean = bundle.getParcelable(ANIM_KEY)
        } else {
            val fragment = baseFragment()
            if (fragment != null) {
                stackManager.replace(fragment)
            }
        }
    }

    /**
     * 对于需要更多定制化view的，可以实现这个方法。对于不需要定义的可不实现
     *
     * @return
     */
    fun contentView(): ViewGroup? {
        return null
    }

    /**
     * 内部fragment的的容器
     *
     * @return
     */
    private fun fragmentContainer(): FrameLayout {
        val rootLayout = FrameLayout(this)
        rootLayout.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        rootLayout.id = Resource.getId(this, "FrameLayoutId")
        return rootLayout
    }

    /**
     * activity对应的根部fragment
     *
     * @return
     */
    abstract fun baseFragment(): BaseFragment?

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
            return
        }

//        curAttachedFragment.setFragmentResult(0, null)
        popPage()
    }

    /**
     * 界面弹出
     */
    fun popPage() {

        MainThreadUtils.post(Runnable {
            supportFragmentManager.popBackStackImmediate()
            FragmentStack.getInstance().pop(javaClass.canonicalName);
            // 通知前一个页面，它被展示了
//            curAttachedFragment.onReShowResume()
        })

    }

    override fun finish() {
        super.finish()
//        if (mAnimBean != null) {
//            overridePendingTransition(mAnimBean!!.enter, mAnimBean!!.exit)
//        }
    }

    companion object {
        @JvmField
        val ANIM_KEY = "bundle_anim_data"
        @JvmField
        val NEW_FRAGMENT_KEY = "bundle_new_fragment"
    }


//    override fun onBackPressed() {
//        val fragmentManager = supportFragmentManager
//        val count = fragmentManager.backStackEntryCount
//        if (count > 0) {
//            val entry = fragmentManager.getBackStackEntryAt(count - 1)
//            val fragment = fragmentManager.findFragmentByTag(entry.name) as BaseFragment?
//            if (fragment != null && fragment.isAdded && !fragment.dispatchBackPressed()) {
//                if (count == 1) {
//                    if (!handleBackPressed()) {
//                        ActivityCompat.finishAfterTransition(this)
//                    }
//                } else {
//                    dismissFragment(fragment)
//                }
//            }
//        } else {
//            super.onBackPressed()
//        }
//    }


}