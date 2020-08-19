package com.yyxnb.module_wanandroid.ui.tree

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.adapter.WanNavigationAdapter
import com.yyxnb.module_wanandroid.bean.WanNavigationBean
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel

/**
 * 导航.
 */
@BindRes(subPage = true)
class WanNavigationFragment : BaseFragment() {

    private var binding: IncludeRlRvLayoutBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    @BindViewModel
    lateinit var mViewModel: WanTreeViewModel
    private var mAdapter: WanNavigationAdapter? = null

    override fun initLayoutResId(): Int {
        return R.layout.include_rl_rv_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.mRefreshLayout
        mRecyclerView = binding!!.mRecyclerView
        mRefreshLayout!!.setEnableLoadMore(false)
    }

    override fun initViewData() {
        mAdapter = WanNavigationAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mRefreshLayout!!.setOnRefreshListener { refreshLayout: RefreshLayout? -> mViewModel.getNavigationData() }
    }

    override fun initObservable() {
        mViewModel.getNavigationData()
        mViewModel.navigationData.observe(this, { data: List<WanNavigationBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                mAdapter!!.setDataItems(data)
            }
        })
    }
}