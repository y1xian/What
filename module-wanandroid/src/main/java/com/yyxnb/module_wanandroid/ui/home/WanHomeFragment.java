package com.yyxnb.module_wanandroid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.weight.GlideImageLoader;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.adapter.WanHomeAdapter;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.databinding.FragmentWanHomeBinding;
import com.yyxnb.module_wanandroid.ui.WanWebActivity;
import com.yyxnb.module_wanandroid.viewmodel.WanHomeViewModel;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.module_wanandroid.config.DataConfig.DATA_SIZE;

/**
 * 首页.
 */
@BindRes(subPage = true)
public class WanHomeFragment extends BaseFragment {

    @BindViewModel
    public WanHomeViewModel mViewModel;

    private FragmentWanHomeBinding binding;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private WanHomeAdapter mAdapter;
    private Banner mBanner;

    private int mPage = 0;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRefreshLayout = binding.iRvContent.srlContent;
        mRecyclerView = binding.iRvContent.rvContent;

//        mRefreshLayout.setEnablePureScrollMode(false)
//                .setEnableRefresh(true).setEnableLoadMore(true);

        binding.ivSearch.setOnClickListener(v -> {
            startFragment(new WanSearchFragment());
        });
    }

    @Override
    public void initViewData() {
        mAdapter = new WanHomeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        View mHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_home_header_layout, mRecyclerView, false);
        mBanner = mHeader.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader());
        mAdapter.addHeaderView(mHeader);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
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
                mViewModel.getAritrilList(mPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 0;
                mViewModel.getBanner();
                mViewModel.getTopAritrilList();
//                mViewModel.reqHomeList(mPage);
            }
        });
    }

    @Override
    public void initObservable() {
        mViewModel.getBanner();
        mViewModel.getTopAritrilList();

        mViewModel.bannerData.observe(this, data -> {

            List<String> list = new ArrayList<>();
            if (data != null) {
                for (WanAriticleBean s : data) {
                    list.add(s.imagePath);
                }
                //设置图片集合
                mBanner.setImages(list);
                mBanner.start();
            }
        });

        mViewModel.topArticleData.observe(this, data -> {
            if (data != null) {
                mAdapter.setDataItems(data);
                mViewModel.getAritrilList(mPage);
            }
        });

        mViewModel.homeListData.observe(this, data -> {
            log("加载完成");
            mRefreshLayout.finishRefresh().finishLoadMore();
            if (data.datas != null) {
//                if (mPage == 0) {
//                    mAdapter.addDataItem(data.datas);
//                } else {
                mAdapter.addDataItem(data.datas);
//                }
                if (data.size < DATA_SIZE) {
                    mRefreshLayout.finishRefreshWithNoMoreData();
                }
            }
        });
    }

    @Override
    public void onVisible() {
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onInVisible() {
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }
}