package com.yyxnb.arch.interfaces

import com.yyxnb.arch.base.BaseActivity

interface OnActivityStatusChangeListener {

    fun onActivityCreate(activity: BaseActivity)

    fun onActivityDestroy(activity: BaseActivity)

}