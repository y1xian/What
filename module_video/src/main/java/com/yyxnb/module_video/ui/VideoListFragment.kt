package com.yyxnb.module_video.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDecoration
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.adapter.SimpleOnItemClickListener
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R
import com.yyxnb.module_video.adapter.VideoListAdapter
import com.yyxnb.module_video.config.DataConfig.tikTokBeans
import com.yyxnb.module_video.databinding.FragmentVideoListBinding

/**
 * 视频列表
 */
@BindRes(subPage = true)
class VideoListFragment : BaseFragment() {

    private var mAdapter: VideoListAdapter? = null
    private var binding: FragmentVideoListBinding? = null
    private var mRecyclerView: RecyclerView? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRecyclerView = binding!!.mRecyclerView
        mAdapter = VideoListAdapter()
        mRecyclerView!!.layoutManager = GridLayoutManager(context, 3)
        val decoration = ItemDecoration(context)
        decoration.setDividerColor(Color.WHITE)
                .setDividerHeight(1)
                .setDividerWidth(1)
                .setDrawBorderTopAndBottom(true).isDrawBorderLeftAndRight = true
        mRecyclerView!!.addItemDecoration(decoration)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun initViewData() {
        mAdapter!!.setDataItems(tikTokBeans)
        mAdapter!!.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                toast(" p $position")
                //                startFragment(VideoPlayFragment.newInstance(position, mAdapter.getData()));
            }
        })
    }
}