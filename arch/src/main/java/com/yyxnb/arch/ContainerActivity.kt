package com.yyxnb.arch

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.yyxnb.arch.base.BaseActivity
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.arch.common.AppConfig
import com.yyxnb.arch.ext.tryCatch
import com.yyxnb.arch.utils.log.LogUtils

/**
 * Description: 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 *
 * @author : yyx
 * @date ：2018/6/9
 */
class ContainerActivity : BaseActivity() {

    override fun initLayoutResId(): Int = R.layout.base_nav_content

    override fun initView(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        tryCatch({
            if (null == intent) {
                throw RuntimeException("you must provide a page info to display")
            }
            val fragmentName = intent.getStringExtra(AppConfig.FRAGMENT)
            if (fragmentName.isEmpty()) {
                throw IllegalArgumentException("can not find page fragmentName")
            }
            val fragmentClass = Class.forName(fragmentName)
            val fragment = fragmentClass.newInstance() as BaseFragment
            intent?.getBundleExtra(AppConfig.BUNDLE)?.let {
                fragment.arguments = it
            }

            setRootFragment(fragment, R.id.fragmentContent)

        }, {
            LogUtils.e(it.message.toString())
        })

    }
}

