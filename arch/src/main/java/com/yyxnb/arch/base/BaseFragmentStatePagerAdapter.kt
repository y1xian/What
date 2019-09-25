package com.yyxnb.arch.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter


/**
 * Description:每页都是一个Fragment，当Fragment不被需要时（比如不可见），整个Fragment都会被销毁，
 * 除了saved state被保存外（保存下来的bundle用于恢复Fragment实例）。所以它适用于很多页的情况
 *
 * ps: FragmentStatePagerAdapter 继承自 PagerAdapter,当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，
 *  生成新的页面(就像 ListView 的实现一样)。这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 * @author : yyx
 * @date ：2018/6/9
 */
class BaseFragmentStatePagerAdapter : FragmentStatePagerAdapter {

    private var list: List<Fragment>? = null
    private lateinit var titles: Any

    constructor(fm: FragmentManager, list: List<Fragment>) : super(fm) {
        this.list = list
    }

    constructor(fm: FragmentManager, titles: Any, list: List<Fragment>) : super(fm) {
        this.list = list
        this.titles = titles
        notifyDataSetChanged()
    }

    //设置每页的标题
    override fun getPageTitle(position: Int): CharSequence? {
        return if (this::titles.isInitialized) {
            when (titles) {
                is Array<*> -> (titles as Array<*>)[position]?.toString()
                is List<*> -> (titles as List<*>)[position]?.toString()
                else -> throw IllegalAccessException("titles must be array or list")
            }
        } else {
            super.getPageTitle(position)
        }
    }

    //设置每一页对应的fragment
    override fun getItem(position: Int): Fragment {
        return list!![position]
    }

    //fragment的数量
    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}