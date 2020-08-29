package com.yyxnb.module_wanandroid.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.widget.interfaces.OnSelectListener
import com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN_FRAGMENT
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.view.tabbar.*
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.databinding.FragmentWanMainBinding
import com.yyxnb.module_wanandroid.ui.home.WanHomeFragment
import com.yyxnb.module_wanandroid.ui.project.WanProjectFragment
import com.yyxnb.module_wanandroid.ui.publicnumber.WanPublicFragment
import com.yyxnb.module_wanandroid.ui.tree.WanTreeFragment
import java.util.*

/**
 * 玩安卓 主页.
 */
@Route(path = WAN_MAIN_FRAGMENT)
@BindRes
class WanMainFragment : BaseFragment() {

    private var binding: FragmentWanMainBinding? = null
    private var fragments: ArrayList<Fragment>? = null
    private var tabs: MutableList<Tab>? = null
    private var mTabLayout: TabBarView? = null
    private var currentIndex = 0

    private var isAddeds = false

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mTabLayout = binding!!.mTabLayout
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments!!.add(WanHomeFragment())
            fragments!!.add(WanTreeFragment())
            fragments!!.add(WanProjectFragment())
            fragments!!.add(WanPublicFragment())
            tabs = ArrayList()
            tabs!!.add(Tab(context, "首页", R.mipmap.ic_titlebar_progress))
            tabs!!.add(Tab(context, "分类", R.mipmap.ic_titlebar_progress))
            tabs!!.add(Tab(context, "项目", R.mipmap.ic_titlebar_progress))
            tabs!!.add(Tab(context, "公众号", R.mipmap.ic_titlebar_progress))
        }
        mTabLayout!!.setTab(tabs)
        mTabLayout!!.onSelectListener = object : OnSelectListener {
            override fun onClick(v: View, position: Int, text: String?) {
                changeView(position)
            }
        }
        changeView(0)
    }

    //设置Fragment页面
    private fun changeView(index: Int) {
        if (currentIndex == index && isAddeds) {
            //重复点击
            return
        }
        isAddeds = true
        //开启事务
        val ft = childFragmentManager.beginTransaction()
        //隐藏当前Fragment
        ft.hide(fragments!![currentIndex])
        //判断Fragment是否已经添加
        if (!fragments!![index].isAdded) {
            ft.add(R.id.fragment_content_view, fragments!![index]).show(fragments!![index])
        } else {
            //显示新的Fragment
            ft.show(fragments!![index])
        }
        ft.commitAllowingStateLoss()
        currentIndex = index
    }
}

