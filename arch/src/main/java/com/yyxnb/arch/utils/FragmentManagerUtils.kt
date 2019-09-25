package com.yyxnb.arch.utils

import android.view.View
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.arch.interfaces.OnFragmentStatusChangeListener
import java.io.Serializable
import java.util.*

object FragmentManagerUtils : Serializable {

    private var fragmentStack: Stack<BaseFragment>? = null

    private var onFragmentStatusChangeListener: OnFragmentStatusChangeListener? = null

    val count: Int
        get() = fragmentStack!!.size

    fun setOnFragmentStatusChangeListener(onFragmentStatusChangeListener: OnFragmentStatusChangeListener) {
        FragmentManagerUtils.onFragmentStatusChangeListener = onFragmentStatusChangeListener
    }

    fun createFragment(fragment: BaseFragment, view: View) {
        fragment?.let {
            if (null == fragmentStack) {
                fragmentStack = Stack()
            }
            fragmentStack!!.add(it)
            onFragmentStatusChangeListener?.onFragmentCreate(it, view)
        }

    }

    fun destroyFragment(fragment: BaseFragment) {
        fragment?.let {
            fragmentStack?.remove(it)
            onFragmentStatusChangeListener?.onFragmentDestroy(it)
            System.gc()
        }

    }

}