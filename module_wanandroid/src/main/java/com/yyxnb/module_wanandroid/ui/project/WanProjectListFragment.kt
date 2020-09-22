package com.yyxnb.module_wanandroid.ui.project

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
import com.yyxnb.module_wanandroid.adapter.WanProjectAdapter
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.DataConfig
import com.yyxnb.module_wanandroid.viewmodel.WanProjectViewModel

/**
 * 项目 list数据.
 */
@BindRes(subPage = true)
class WanProjectListFragment : BaseFragment() {

    private var binding: IncludeRlRvLayoutBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    @BindViewModel
    lateinit var mViewModel: WanProjectViewModel
    private var mAdapter: WanProjectAdapter? = null
    private var mPage = 0
    private var mId = 0
    private var isNew = false

    override fun initLayoutResId(): Int {
        return R.layout.include_rl_rv_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.mRefreshLayout
        mRecyclerView = binding!!.mRecyclerView
        if (arguments != null) {
            mId = requireArguments().getInt("id", mId)
            isNew = requireArguments().getBoolean("isNew", false)
        }
    }

    override fun initViewData() {
        mAdapter = WanProjectAdapter()
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
                if (isNew) {
                    mViewModel.getProjecNewData(mPage)
                } else {
                    mViewModel.getProjecDataByType(mPage + 1, mId)
                }
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                if (isNew) {
                    mViewModel.getProjecNewData(mPage)
                } else {
                    mViewModel.getProjecDataByType(mPage + 1, mId)
                }
            }
        })
    }

    override fun initObservable() {
        if (isNew) {
            mViewModel.getProjecNewData(mPage)
        } else {
            mViewModel.getProjecDataByType(mPage + 1, mId)
        }
        mViewModel.projecNewData.observe(this, { data: List<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                if (mPage == 0) {
                    mAdapter!!.setDataItems(data)
                } else {
                    mAdapter!!.addDataItem(data)
                }
                if (data.size < DataConfig.DATA_SIZE) {
                    mRefreshLayout!!.finishRefreshWithNoMoreData()
                }
            }
        })
        mViewModel.projecDataByType.observe(this, { data: WanStatus<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                if (mPage == 1) {
                    mAdapter!!.setDataItems(data.datas)
                } else {
                    mAdapter!!.addDataItem(data.datas)
                }
                if (data.datas.size < DataConfig.DATA_SIZE) {
                    mRefreshLayout!!.finishRefreshWithNoMoreData()
                }
            }
        })
    }

    companion object {
        fun newInstance(isNew: Boolean, id: Int): WanProjectListFragment {
            val args = Bundle()
            args.putInt("id", id)
            args.putBoolean("isNew", isNew)
            val fragment = WanProjectListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}