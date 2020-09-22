package com.yyxnb.module_wanandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.adapter.SimpleOnItemClickListener
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.weight.GlideImageLoader
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.bean.WanStatus
import com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE
import com.yyxnb.module_wanandroid.databinding.FragmentWanHomeBinding
import com.yyxnb.module_wanandroid.ui.WanWebActivity
import com.yyxnb.module_wanandroid.viewmodel.WanHomeViewModel
import java.util.*

/**
 * 首页.
 */
@BindRes(subPage = true)
class WanHomeFragment : BaseFragment() {

    @BindViewModel
    lateinit var mViewModel: WanHomeViewModel

    private var binding: FragmentWanHomeBinding? = null
    private var mRefreshLayout: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: WanHomeAdapter? = null
    private var mBanner: Banner? = null
    private var mPage = 0

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRefreshLayout = binding!!.iRvLayout.mRefreshLayout
        mRecyclerView = binding!!.iRvLayout.mRecyclerView

//        mRefreshLayout.setEnablePureScrollMode(false)
//                .setEnableRefresh(true).setEnableLoadMore(true);
        binding!!.ivSearch.setOnClickListener { v: View? -> startFragment(WanSearchFragment()) }
    }

    override fun initViewData() {
        mAdapter = WanHomeAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        val mHeader = LayoutInflater.from(context).inflate(R.layout.item_home_header_layout, mRecyclerView, false)
        mBanner = mHeader.findViewById(R.id.mBanner)
        mBanner!!.setImageLoader(GlideImageLoader())
        mAdapter!!.addHeaderView(mHeader)
        mAdapter!!.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                val intent = Intent(getActivity, WanWebActivity::class.java)
                intent.putExtra("title", mAdapter!!.getItem(position)!!.title)
                intent.putExtra("url", mAdapter!!.getItem(position)!!.link)
                startActivity(intent)
            }
        })
        mRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel.getAritrilList(mPage)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel.getBanner()
                mViewModel.getTopAritrilList()
                //                mViewModel.reqHomeList(mPage);
            }
        })
    }

    override fun initObservable() {
        mViewModel.getBanner()
        mViewModel.getTopAritrilList()
        mViewModel.bannerData.observe(this, { data: List<WanAriticleBean>? ->
            val list: MutableList<String?> = ArrayList()
            if (data != null) {
                for (s in data) {
                    list.add(s.imagePath)
                }
                //设置图片集合
                mBanner!!.setImages(list)
                mBanner!!.start()
            }
        })
        mViewModel.topArticleData.observe(this, { data: List<WanAriticleBean>? ->
            if (data != null) {
                mAdapter!!.setDataItems(data)
                mViewModel.getAritrilList(mPage)
            }
        })
        mViewModel.homeListData.observe(this, { data: WanStatus<WanAriticleBean>? ->
            mRefreshLayout!!.finishRefresh().finishLoadMore()
            if (data != null) {
//                if (mPage == 0) {
//                    mAdapter.addDataItem(data.datas);
//                } else {
                mAdapter!!.addDataItem(data.datas)
                //                }
                if (data.datas.size < DATA_SIZE) {
                    mRefreshLayout!!.finishRefreshWithNoMoreData()
                }
            }
        })
    }

    override fun onVisible() {
        if (mBanner != null) {
            mBanner!!.startAutoPlay()
        }
    }

    override fun onInVisible() {
        if (mBanner != null) {
            mBanner!!.stopAutoPlay()
        }
    }
}