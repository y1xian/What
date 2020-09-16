package com.yyxnb.module_wanandroid.ui.tree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.SimpleOnItemClickListener;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 广场.
 */
@BindRes(subPage = true)
public class WanSquareFragment extends BaseFragment {

    private IncludeRlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanTreeViewModel mViewModel;
    private WanHomeAdapter mAdapter;
    private int mPage;

    @Override
    public int initLayoutResId() {
        return R.layout.include_rl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.mRefreshLayout;
        mRecyclerView = binding.mRecyclerView;

    }

    @Override
    public void initViewData() {
        mAdapter = new WanHomeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);

            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.getSquareData(mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.getSquareData(mPage);
            }
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getSquareData(mPage);

        mViewModel.squareData.observe(this, data -> {
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null) {
                if (mPage == 0) {
                    mAdapter.setDataItems(data.datas);
                } else {
                    mAdapter.addDataItem(data.datas);
                }
                if (data.size < DATA_SIZE) {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            }
        });
    }
}