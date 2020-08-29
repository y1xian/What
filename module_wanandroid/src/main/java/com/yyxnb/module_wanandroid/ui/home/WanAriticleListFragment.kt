package com.yyxnb.module_wanandroid.ui.home

import android.content.Intent
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
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE
import com.yyxnb.module_wanandroid.databinding.FragmentWanAriticleListBinding
import com.yyxnb.module_wanandroid.ui.WanWebActivity
import com.yyxnb.module_wanandroid.viewmodel.WanSearchViewModel

/**
 * 列表.
 */
@BindRes
class WanAriticleListFragment : BaseFragment() {

    private var binding: FragmentWanAriticleListBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null

    @BindViewModel
    lateinit var mViewModel: WanSearchViewModel
    private var mAdapter: WanHomeAdapter? = null
    private var mKey: String? = null
    private var mPage = 0

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_ariticle_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.iRvLayout.mRefreshLayout
        mRecyclerView = binding!!.iRvLayout.mRecyclerView
        mKey = initArguments()!!.getString("key", "")
        binding!!.iTitle.mTitle.centerTextView.text = mKey
        binding!!.iTitle.mTitle.setBackListener { v: View? -> finish() }
    }

    override fun initViewData() {
        mAdapter = WanHomeAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel.getSearchDataByKey(mPage, mKey)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel.getSearchDataByKey(mPage, mKey)
            }
        })

        mAdapter?.setOnItemClickListener(object :MultiItemTypeAdapter.SimpleOnItemClickListener(){
            override fun onItemClick(view: View?, holder: BaseViewHolder?, position: Int) {
                super.onItemClick(view, holder, position)
                val intent = Intent(getActivity,WanWebActivity::class.java)
                intent.putExtra("title",mAdapter!!.getItem(position)!!.title)
                intent.putExtra("url",mAdapter!!.getItem(position)!!.link)
                startActivity(intent)
            }
        })
    }

    override fun initObservable() {
        mViewModel.getSearchDataByKey(mPage, mKey)

        mViewModel.searchDataByKey.observe(this, { data: WanStatus<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
                if (mPage == 0) {
                    mAdapter!!.addDataItem(data.datas)
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