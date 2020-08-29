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
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.ui.WanWebActivity;
import com.yyxnb.module_wanandroid.viewmodel.WanPublicViewModel;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 公众号 list.
 */
@BindRes(subPage = true)
public class WanPublicListFragment extends BaseFragment {

    private IncludeRlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

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
        return R.layout.include_rl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.mRefreshLayout;
        mRecyclerView = binding.mRecyclerView;

        mId = getArguments().getInt("id", 0);
    }

    @Override
    public void initViewData() {
        mAdapter = new WanHomeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                Intent intent = new Intent(getContext(), WanWebActivity.class);
                intent.putExtra("title",mAdapter.getItem(position).title);
                intent.putExtra("url",mAdapter.getItem(position).link);
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
                if (mPage == 1) {
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