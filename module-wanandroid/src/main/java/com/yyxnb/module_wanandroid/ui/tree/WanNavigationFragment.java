package com.yyxnb.module_wanandroid.ui.tree;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.databinding.IncludeSrlRvLayoutBinding;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanNavigationAdapter;
import com.yyxnb.module_wanandroid.viewmodel.WanTreeViewModel;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

/**
 * 导航.
 */
@BindRes(subPage = true)
public class WanNavigationFragment extends BaseFragment {

    private IncludeSrlRvLayoutBinding binding;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanTreeViewModel mViewModel;
    private WanNavigationAdapter mAdapter;

    @Override
    public int initLayoutResId() {
        return R.layout.include_srl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.srlContent;
        mRecyclerView = binding.rvContent;

        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void initViewData() {
        mAdapter = new WanNavigationAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mViewModel.getNavigationData();
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getNavigationData();

        mViewModel.navigationData.observe(this,data->{
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null){
                mAdapter.setDataItems(data);
            }
        });
    }
}