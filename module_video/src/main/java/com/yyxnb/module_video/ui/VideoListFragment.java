package com.yyxnb.module_video.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.lib_adapter.ItemDecoration;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.adapter.VideoListAdapter;
import com.yyxnb.module_video.config.DataConfig;
import com.yyxnb.module_video.databinding.FragmentVideoListBinding;

/**
 * 视频列表
 */
@BindRes(subPage = true)
public class VideoListFragment extends BaseFragment {

    private VideoListAdapter mAdapter;
    private FragmentVideoListBinding binding;

    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.mRecyclerView;
        mAdapter = new VideoListAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerColor(Color.WHITE)
                .setDividerHeight(1)
                .setDividerWidth(1)
                .setDrawBorderTopAndBottom(true)
                .setDrawBorderLeftAndRight(true);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initViewData() {
        mAdapter.setDataItems(DataConfig.getTikTokBeans());

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                toast(" p " + position);
//                startFragment(VideoPlayFragment.newInstance(position, mAdapter.getData()));
            }
        });
    }
}