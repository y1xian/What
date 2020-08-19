package com.yyxnb.common.interfaces

import android.view.View

/**
 * 选择回调
 *
 * @author yyx
 */
interface OnSelectListener {
    /**
     * 点击回调
     *
     * @param v        view
     * @param position 当前位置
     * @param text     内容
     */
    fun onClick(v: View, position: Int, text: String?)
}