package com.yyxnb.module_video.ui.find

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
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R
import com.yyxnb.module_video.adapter.VideoFindAdapter
import com.yyxnb.module_video.config.DataConfig.tikTokBeans
import com.yyxnb.module_video.databinding.FragmentVideoFindBinding

/**
 * 发现
 */
@BindRes(subPage = true)
class VideoFindFragment : BaseFragment() {

    private var mAdapter: VideoFindAdapter? = null
    private var binding: FragmentVideoFindBinding? = null
    private var mRecyclerView: RecyclerView? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_find
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRecyclerView = binding!!.mRecyclerView
        mAdapter = VideoFindAdapter()
        mRecyclerView!!.layoutManager = GridLayoutManager(context, 2)
        val decoration = ItemDecoration(context)
        decoration.setDividerColor(Color.WHITE)
                .setDividerHeight(2)
                .setDividerWidth(2)
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
                //                startFragment(VideoPlayFragment.newInstance(position, mAdapter.getData()));
            }
        })
    }

    override fun onVisible() {
        w("find v")
    }

    override fun onInVisible() {
        w("find iv")
    }
}