package com.yyxnb.module_widget.ui;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.adapter.MainListAdapter;
import com.yyxnb.module_widget.config.DataConfig;
import com.yyxnb.module_widget.databinding.IncludeWidgetSrlRvLayoutBinding;
import com.yyxnb.what.adapter.ItemDecoration;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/02
 * 描    述：系统类
 * ================================================
 */
public class WidgetSystemFragment extends BaseFragment {

    private MainListAdapter mAdapter = new MainListAdapter();
    private IncludeWidgetSrlRvLayoutBinding binding;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.include_widget_srl_rv_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        binding = getBinding();
        mRecyclerView = binding.rvContent;

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                setMenu(mAdapter.getData().get(position).key);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            if (mAdapter.getData().get(position).type == 1) {
                return 2;
            }
            return 1;
        });

        mRecyclerView.setLayoutManager(manager);
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerWidth(5);
        decoration.setDividerHeight(5);
        decoration.setDrawBorderTopAndBottom(true);
        decoration.setDrawBorderLeftAndRight(true);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initViewData() {
        mAdapter.setDataItems(DataConfig.getSystemBeans());
    }

    private void setMenu(String key) {

    }
}