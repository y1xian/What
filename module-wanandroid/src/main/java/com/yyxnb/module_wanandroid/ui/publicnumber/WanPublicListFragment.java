package com.yyxnb.module_wanandroid.ui.publicnumber;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.common_res.databinding.IncludeSrlStatusRvLayoutBinding;
import com.yyxnb.lib_adapter.base.BaseViewHolder;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.ui.WanWebActivity;
import com.yyxnb.module_wanandroid.viewmodel.WanPublicViewModel;
import com.yyxnb.lib_view.status.StatusView;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 公众号 list.
 */
@BindRes(subPage = true)
public class WanPublicListFragment extends BaseFragment {

    private IncludeSrlStatusRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private StatusView statusView;

    @BindViewModel
    WanPublicViewModel mViewModel;

    private WanHomeAdapter mAdapter;
    private int mPage = 1;
    private int mId;

    public static WanPublicListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        WanPublicListFragment fragment = new WanPublicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.include_srl_status_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.srlContent;
        mRecyclerView = binding.rvContent;

        mId = getArguments().getInt("id", 0);

//        statusView = StatusView.init(this,R.id.mRefreshLayout);
        statusView = binding.vStatus;
//        statusView.config(new StatusViewBuilder.Builder().build());
//        statusView.setLoadingView(R.layout.item_wan_home_layout);
        statusView.showLoadingView();
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
                mViewModel.getPublicData(mPage, mId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mViewModel.getPublicData(mPage, mId);
            }
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getPublicData(mPage, mId);

        mViewModel.publicData.observe(this, data -> {
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null) {
                statusView.showContentView();
                if (mPage == 1) {
                    mAdapter.setDataItems(data.datas);
                } else {
                    mAdapter.addDataItem(data.datas);
                }
                if (data.size < DATA_SIZE) {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            } else {
                statusView.showEmptyView();
            }
        });
    }
}