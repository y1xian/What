package com.yyxnb.arch.interfaces

import android.app.Activity

interface IOnActivityStatusChangeListener {

    fun onActivityCreate(activity: Activity)

    fun onActivityDestroy(activity: Activity)

}