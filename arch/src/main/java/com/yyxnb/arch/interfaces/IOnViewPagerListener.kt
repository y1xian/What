package com.yyxnb.yyxarch.interfaces

import android.view.View

interface IOnViewPagerListener {
    /**
     * 初始化
     */
    fun onInitComplete(view: View)

    /**
     * 释放
     */
    fun onPageRelease(isNext: Boolean, position: Int, view: View)

    /**
     * 选中
     */
    fun onPageSelected(position: Int, isSelected: Boolean, isBottom: Boolean, view: View)
}