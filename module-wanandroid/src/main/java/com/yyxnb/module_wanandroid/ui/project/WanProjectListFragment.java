package com.yyxnb.module_wanandroid.ui.project;

import android.content.Intent;
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
import com.yyxnb.module_wanandroid.adapter.WanProjectAdapter;
import com.yyxnb.module_wanandroid.config.DataConfig;
import com.yyxnb.module_wanandroid.ui.WanWebActivity;
import com.yyxnb.module_wanandroid.viewmodel.WanProjectViewModel;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

/**
 * 项目 list数据.
 */
@BindRes(subPage = true)
public class WanProjectListFragment extends BaseFragment {

    private IncludeSrlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanProjectViewModel mViewModel;

    private WanProjectAdapter mAdapter;
    private int mPage;
    private int mId;
    private boolean isNew;

    public static WanProjectListFragment newInstance(boolean isNew, int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putBoolean("isNew", isNew);
        WanProjectListFragment fragment = new WanProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.include_srl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.srlContent;
        mRecyclerView = binding.rvContent;

        if (getArguments() != null) {
            mId = getArguments().getInt("id", mId);
            isNew = getArguments().getBoolean("isNew", false);
        }

    }

    @Override
    public void initViewData() {
        mAdapter = new WanProjectAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                Intent intent = new Intent(getContext(), WanWebActivity.class);
                intent.putExtra("title", mAdapter.getItem(position).title);
                intent.putExtra("url", mAdapter.getItem(position).link);
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                if (isNew) {
                    mViewModel.getProjecNewData(mPage);
                } else {
                    mViewModel.getProjecDataByType(mPage + 1, mId);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                if (isNew) {
                    mViewModel.getProjecNewData(mPage);
                } else {
                    mViewModel.getProjecDataByType(mPage + 1, mId);
                }
            }
        });
    }

    @Override
    public void initObservable() {
        if (isNew) {
            mViewModel.getProjecNewData(mPage);
        } else {
            mViewModel.getProjecDataByType(mPage + 1, mId);
        }

        mViewModel.projecNewData.observe(this, data -> {
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null) {
                if (mPage == 0) {
                    mAdapter.setDataItems(data);
                } else {
                    mAdapter.addDataItem(data);
                }
                if (data.size() < DataConfig.DATA_SIZE) {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            }
        });

        mViewModel.projecDataByType.observe(this, data -> {
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null) {
                if (mPage == 1) {
                    mAdapter.setDataItems(data.datas);
                } else {
                    mAdapter.addDataItem(data.datas);
                }
                if (data.datas.size() < DataConfig.DATA_SIZE) {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            }
        });
    }
}