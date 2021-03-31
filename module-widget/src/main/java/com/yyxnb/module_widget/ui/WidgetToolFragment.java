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
import com.yyxnb.module_widget.ui.tools.DialogFragment;
import com.yyxnb.module_widget.ui.tools.PopupFragment;
import com.yyxnb.module_widget.ui.tools.WidgetTitleFragment;
import com.yyxnb.what.adapter.ItemDecoration;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/02
 * 描    述：控件 自定义view之类的
 * ================================================
 */
public class WidgetToolFragment extends BaseFragment {

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

        mAdapter.setDataItems(DataConfig.getToolsBeans());
    }

    private void setMenu(String key) {
        switch (key) {
            case "title":
                startFragment(new WidgetTitleFragment());
                break;
            case "dialog":
                startFragment(new DialogFragment());
                break;
            case "tag":
//                startFragment(new TagFragment());
            case "popup":
                startFragment(new PopupFragment());
                break;
            default:
                break;
        }
    }

}