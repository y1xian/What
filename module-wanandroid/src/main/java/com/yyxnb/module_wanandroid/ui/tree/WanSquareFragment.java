package com.yyxnb.module_wanandroid.ui.tree;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.databinding.IncludeSrlRvLayoutBinding;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 广场.
 */
@BindRes(subPage = true)
public class WanSquareFragment extends BaseFragment {

    private IncludeSrlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanTreeViewModel mViewModel;
    private WanHomeAdapter mAdapter;
    private int mPage;

    @Override
    public int initLayoutResId() {
        return R.layout.include_srl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.srlContent;
        mRecyclerView = binding.rvContent;

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