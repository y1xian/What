package com.yyxnb.module_wanandroid.ui.tree

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.adapter.SimpleOnItemClickListener
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel

/**
 * 广场.
 */
@BindRes(subPage = true)
class WanSquareFragment : BaseFragment() {

    private var binding: IncludeRlRvLayoutBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    @BindViewModel
    lateinit var mViewModel: WanTreeViewModel
    private var mAdapter: WanHomeAdapter? = null
    private var mPage = 0
    override fun initLayoutResId(): Int {
        return R.layout.include_rl_rv_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.mRefreshLayout
        mRecyclerView = binding!!.mRecyclerView
    }

    override fun initViewData() {
        mAdapter = WanHomeAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
            }
        })
        mRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel.getSquareData(mPage)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel.getSquareData(mPage)
            }
        })
    }

    override fun initObservable() {
        mViewModel.getSquareData(mPage)
        mViewModel.squareData.observe(this, { data: WanStatus<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                if (mPage == 0) {
                    mAdapter!!.setDataItems(data.datas)
                } else {
                    mAdapter!!.addDataItem(data.datas)
                }
                if (data.datas.size < DATA_SIZE) {
                    mRefreshLayout!!.finishRefreshWithNoMoreData()
                }
            }
        })
    }
}