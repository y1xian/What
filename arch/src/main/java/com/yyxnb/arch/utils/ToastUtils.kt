package com.yyxnb.arch.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.yyxnb.arch.Arch
import java.io.Serializable


/**
 * 自定义Toast
 * @author yyx
 */
@SuppressLint("StaticFieldLeak")
object ToastUtils : Serializable {

    private val mContext = Arch.context

    /** Toast对象  */
    private var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun normal(msg: CharSequence) {
        MainThreadUtils.post(Runnable {
            mToast?.apply {
                //如果当前Toast没有消失， 直接显示内容，不需要重新设置
                setText(msg)
            } ?: apply {
                mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
            }
            mToast?.show()
        })
    }


    /**
     * 在 App 处于 Debug 的环境下弹 Toast。
     * @param msg
     */
    fun debug(msg: CharSequence) {
        if (Arch.isDebug) {
            normal(msg)
        }
    }

}
