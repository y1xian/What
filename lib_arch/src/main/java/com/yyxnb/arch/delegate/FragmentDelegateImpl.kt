package com.yyxnb.arch.delegate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.arch.utils.AppManager.fragmentDelegates
import com.yyxnb.common.utils.MainThreadUtils.post
import java.lang.reflect.Field

/**
 * FragmentLifecycleCallbacks 监听 Fragment 生命周期
 * PS ：先走 Fragment 再走 FragmentLifecycleCallbacks
 *
 * @author yyx
 */
class FragmentDelegateImpl(
        private var mFragmentManager: FragmentManager?,
        private var mFragment: Fragment?
) : IFragmentDelegate {

    private var iFragment: IFragment? = mFragment as IFragment
    private var delegate: FragmentDelegate? = null

    override fun onAttached(context: Context?) {
        delegate = iFragment!!.getBaseDelegate()
        if (delegate != null) {
            delegate!!.onAttach(context)
            mFragment!!.lifecycle.addObserver(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        if (delegate != null) {
            delegate!!.onCreate(savedInstanceState)
            mFragment!!.lifecycle.addObserver(iFragment!!)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initDeclaredFields()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (delegate != null) {
            delegate!!.onActivityCreated(savedInstanceState)
        }
    }

    override fun onStarted() {}
    override fun onResumed() {
        if (delegate != null) {
            delegate!!.onResume()
        }
    }

    override fun onPaused() {
        if (delegate != null) {
            delegate!!.onPause()
        }
    }

    override fun onStopped() {}
    override fun onSaveInstanceState(outState: Bundle?) {}
    override fun onViewDestroyed() {
        if (delegate != null) {
            delegate!!.onDestroyView()
        }
    }

    override fun onDestroyed() {
        if (delegate != null) {
            delegate!!.onDestroy()
            delegate = null
        }
        fragmentDelegates!!.remove(iFragment.hashCode())
        mFragmentManager = null
        mFragment = null
        iFragment = null
    }

    override fun onDetached() {}
    override val isAdd: Boolean
        get() = mFragment!!.isAdded

    fun initDeclaredFields() {
        post(Runnable {
            val declaredFields = iFragment!!.javaClass.getDeclaredFields()
            for (field in declaredFields) {
                // 允许修改反射属性
                field.isAccessible = true

                /*
                 *  根据 {@link BindViewModel } 注解, 查找注解标示的变量（ViewModel）
                 *  并且 创建 ViewModel 实例, 注入到变量中
                 */
                val viewModel = field.getAnnotation(BindViewModel::class.java)
                if (viewModel != null) {
                    try {
                        field[iFragment] = getViewModel(field, viewModel.isActivity)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun getViewModel(field: Field?, isActivity: Boolean): ViewModel {
        return if (isActivity) {
            ViewModelFactory.createViewModel(mFragment!!.activity, field)
        } else {
            ViewModelFactory.createViewModel(mFragment, field)
        }
    }
}