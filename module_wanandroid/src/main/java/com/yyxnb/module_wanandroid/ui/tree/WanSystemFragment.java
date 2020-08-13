package com.yyxnb.module_wanandroid.ui.tree;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.databinding.IncludeRlRvLayoutBinding;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanSystemAdapter;
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel;

/**
 * 体系.
 */
@BindRes(subPage = true)
public class WanSystemFragment extends BaseFragment {

    private IncludeRlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanTreeViewModel mViewModel;
    private WanSystemAdapter mAdapter;

    @Override
    public int initLayoutResId() {
        return R.layout.include_rl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.mRefreshLayout;
        mRecyclerView = binding.mRecyclerView;
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void initViewData() {
        mAdapter = new WanSystemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.getSystemData();
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getSystemData();

        mViewModel.systemData.observe(this,data->{
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null){
                mAdapter.setDataItems(data);
            }
        });
    }
}