package com.yyxnb.module_wanandroid.ui.publicnumber

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE
import com.yyxnb.module_wanandroid.viewmodel.WanPublicViewModel

/**
 * 公众号 list.
 */
@BindRes(subPage = true)
class WanPublicListFragment : BaseFragment() {

    private var binding: IncludeRlRvLayoutBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    @BindViewModel
    lateinit var mViewModel: WanPublicViewModel
    private var mAdapter: WanHomeAdapter? = null
    private var mPage = 1
    private var mId = 0

    override fun initLayoutResId(): Int {
        return R.layout.include_rl_rv_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.mRefreshLayout
        mRecyclerView = binding!!.mRecyclerView
        mId = requireArguments().getInt("id", 0)
    }

    override fun initViewData() {
        mAdapter = WanHomeAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
            }
        })
        mRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel.getPublicData(mPage, mId)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 1
                mViewModel.getPublicData(mPage, mId)
            }
        })
    }

    override fun initObservable() {
        mViewModel.getPublicData(mPage, mId)
        mViewModel.publicData.observe(this, { data: WanStatus<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                if (mPage == 1) {
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

    companion object {
        fun newInstance(id: Int): WanPublicListFragment {
            val args = Bundle()
            args.putInt("id", id)
            val fragment = WanPublicListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}