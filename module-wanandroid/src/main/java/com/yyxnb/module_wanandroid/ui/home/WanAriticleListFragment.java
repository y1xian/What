package com.yyxnb.module_wanandroid.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.databinding.FragmentWanAriticleListBinding;
import com.yyxnb.module_wanandroid.viewmodel.WanSearchViewModel;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 列表.
 */
@BindRes
public class WanAriticleListFragment extends BaseFragment {

    private FragmentWanAriticleListBinding binding;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @BindViewModel
    WanSearchViewModel mViewModel;

    private WanHomeAdapter mAdapter;

    private String mKey;
    private int mPage;


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_ariticle_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        mRefreshLayout = binding.iRvContent.srlContent;
        mRecyclerView = binding.iRvContent.rvContent;

        mKey = initArguments().getString("key", "");

        binding.iTitle.vTitle.getCenterTextView().setText(mKey);
        binding.iTitle.vTitle.setBackListener(v -> {
            setResultCode(888);
            finish();
        });

    }

    @Override
    public void initViewData() {

        mAdapter = new WanHomeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                mViewModel.getSearchDataByKey(mPage, mKey);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.getSearchDataByKey(mPage, mKey);
            }
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getSearchDataByKey(mPage, mKey);

        mViewModel.searchDataByKey.observe(this, data -> {
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data != null) {
                if (mPage == 0) {
                    mAdapter.addDataItem(data.datas);
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