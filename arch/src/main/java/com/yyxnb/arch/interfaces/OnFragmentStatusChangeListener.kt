package com.yyxnb.arch.interfaces

import android.view.View
import com.yyxnb.arch.base.BaseFragment

interface OnFragmentStatusChangeListener {

    fun onFragmentCreate(fragment: BaseFragment, view: View)

    fun onFragmentDestroy(fragment: BaseFragment)

}