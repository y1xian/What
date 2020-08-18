package com.yyxnb.common_base.base

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.common.ArchConfig
import java.lang.ref.WeakReference

/**
 * Description: 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 *
 * @author : yyx
 * @date ：2018/6/9
 */
@BindRes(isContainer = true)
open class ContainerActivity : BaseActivity() {

    private lateinit var mFragment: WeakReference<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mFrameLayout = FrameLayout(this)
        mFrameLayout.id = R.id.content
        setContentView(mFrameLayout)
    }

    open fun initBaseFragment(): Fragment? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun initView(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        //        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            val intent = intent ?: throw RuntimeException("you must provide a page info to display")

            initBaseFragment()?.let {
                mFragment = WeakReference(it)
                if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                    mFragment.get()?.arguments = intent.getBundleExtra(ArchConfig.BUNDLE)
                }
                setRootFragment(mFragment.get() as IFragment, android.R.id.content)
                return
            }

            val fragmentName = intent.getStringExtra(ArchConfig.FRAGMENT)
            require(!fragmentName!!.isEmpty()) { "can not find page fragmentName" }
            val fragmentClass = Class.forName(fragmentName)
            val fragment = fragmentClass.newInstance() as Fragment
            if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                fragment.arguments = intent.getBundleExtra(ArchConfig.BUNDLE)
            }
            setRootFragment((fragment as IFragment), R.id.content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mFragment.clear()
    }
}