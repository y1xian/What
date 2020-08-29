package com.yyxnb.common_base.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yyxnb.arch.action.ArchAction
import com.yyxnb.arch.action.BundleAction
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.base.Java8Observer
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.common.action.AnimAction
import com.yyxnb.widget.action.ClickAction
import com.yyxnb.widget.action.HandlerAction
import java.lang.ref.WeakReference
import java.util.*

/**
 * 懒加载
 *
 * @author yyx
 */
abstract class BaseFragment : Fragment(), IFragment,
        ArchAction, BundleAction, HandlerAction, ClickAction, AnimAction {

    protected val TAG = javaClass.canonicalName
    protected var mContext: WeakReference<Context>? = null
    protected var mActivity: WeakReference<AppCompatActivity>? = null
    protected var mRootView: View? = null
    private val mFragmentDelegate by lazy { getBaseDelegate() }
    private val java8Observer: Java8Observer

    override fun getContext(): Context {
        return mContext!!.get()!!
    }

    init {
        java8Observer = Java8Observer(TAG)
        lifecycle.addObserver(java8Observer)
        lifecycle.addObserver(mFragmentDelegate)
    }

    fun <B : ViewDataBinding> getBinding(): B? {
        DataBindingUtil.bind<B>(mRootView!!)
        return DataBindingUtil.getBinding(mRootView!!)
    }

    override fun sceneId(): String? {
        return UUID.randomUUID().toString()
    }

    override fun initArguments(): Bundle? {
        return mFragmentDelegate.initArguments()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mContext = WeakReference(context)
                mActivity = WeakReference(mContext!!.get() as AppCompatActivity)
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mRootView = mFragmentDelegate.onCreateView(inflater, container, savedInstanceState)
        return mRootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mFragmentDelegate.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mFragmentDelegate.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mFragmentDelegate.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentDelegate.onDestroy()
        mContext?.clear()
        mContext = null
        mRootView = null
        mActivity?.clear()
        mActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(java8Observer)
    }

    @Nullable
    override fun getBundle(): Bundle? {
        return initArguments()
    }

    override fun <V : View> findViewById(id: Int): V {
        return mRootView!!.findViewById(id) as V
    }

    /**
     * 返回.
     */
    fun finish() {
        mFragmentDelegate.finish()
    }

    fun <T : IFragment> startFragment(targetFragment: T) {
        startFragment(targetFragment, 0)
    }

    fun <T : IFragment> startFragment(targetFragment: T, requestCode: Int) {
        try {
            val bundle = initArguments()
            val intent = Intent(mActivity!!.get(), ContainerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(ArchConfig.FRAGMENT, targetFragment.javaClass.getCanonicalName())
            bundle!!.putInt(ArchConfig.REQUEST_CODE, requestCode)
            intent.putExtra(ArchConfig.BUNDLE, bundle)
            mActivity!!.get()!!.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}