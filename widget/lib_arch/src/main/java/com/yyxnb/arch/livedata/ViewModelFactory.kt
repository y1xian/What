package com.yyxnb.arch.livedata

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yyxnb.widget.AppUtils
import java.io.Serializable
import java.lang.reflect.Field

object ViewModelFactory : Serializable {
    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    fun createViewModel(fragment: Fragment?, field: Field?): ViewModel {
        val viewModelClass = AppUtils.getFiledClazz<ViewModel>(field!!)
        return ViewModelProvider(fragment!!).get(viewModelClass)
    }

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    fun createViewModel(activity: FragmentActivity?, field: Field?): ViewModel {
        val viewModelClass = AppUtils.getFiledClazz<ViewModel>(field!!)
        return ViewModelProvider(activity!!).get(viewModelClass)
    }
}