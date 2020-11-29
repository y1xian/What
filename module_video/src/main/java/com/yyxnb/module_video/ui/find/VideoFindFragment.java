package com.yyxnb.module_video.ui.find;

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
import com.yyxnb.module_video.adapter.VideoFindAdapter;
import com.yyxnb.module_video.config.DataConfig;
import com.yyxnb.module_video.databinding.FragmentVideoFindBinding;

/**
 * 发现
 */
@BindRes(subPage = true)
public class VideoFindFragment extends BaseFragment {

    private VideoFindAdapter mAdapter;

    private FragmentVideoFindBinding binding;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.rvContent;
        mAdapter = new VideoFindAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerColor(Color.WHITE)
                .setDividerHeight(2)
                .setDividerWidth(2)
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
//                startFragment(VideoPlayFragment.newInstance(position, mAdapter.getData()));
            }
        });
    }

    @Override
    public void onVisible() {
        log("find v");
    }

    @Override
    public void onInVisible() {
        log("find iv");
    }

}