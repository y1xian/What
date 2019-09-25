package com.yyxnb.arch.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Description:每页都是一个Fragment，并且所有的Fragment实例一直保存在Fragment manager中。所以它适用于少量固定的fragment，
 * 比如一组用于分页显示的标签。除了当Fragment不可见时，它的视图层（view hierarchy）有可能被销毁外，每页的Fragment都会被保存在内存中
 *
 * ps: FragmentPagerAdapter 继承自 PagerAdapter，该类内的每一个生成的 Fragment 都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种
 *
 * @author : yyx
 * @date ：2018/6/9
 */
class BaseFragmentPagerAdapter : FragmentPagerAdapter {

    private var list: List<Fragment>? = null
    private lateinit var titles: Any

    constructor(fm: FragmentManager, list: List<Fragment>) : super(fm) {
        this.list = list
    }

    constructor(fm: FragmentManager, titles: Any, list: List<Fragment>) : super(fm) {
        this.list = list
        this.titles = titles
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
}