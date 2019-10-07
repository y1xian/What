package com.yyxnb.arch.base.mvvm

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.yyxnb.arch.Arch
import com.yyxnb.arch.base.BaseFragment


/**
 * Description: mvvm
 *
 * @author : yyx
 * @date ：2018/6/10
 */
abstract class BaseFragmentVM<VM : BaseViewModel> : BaseFragment() {

    /**
     * ViewModel
     */
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel(Arch.getInstance(this, 0)!!)
        lifecycle.addObserver(mViewModel)
    }

    override fun initViewData() {
//        super.initViewData()
        initObservable()
    }

    /**
     * 初始化界面观察者的监听
     * 接收数据结果
     */
    open fun initObservable() {}

    /**
     * 初始化ViewModel
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    private fun initViewModel(modelClass: Class<VM>): VM {
        return ViewModelProviders.of(mActivity).get(modelClass)
    }

    override fun onDestroy() {
        super.onDestroy()
        //移除LifecycleObserver
        lifecycle.removeObserver(mViewModel)
        this.mViewModel to null
    }
}
