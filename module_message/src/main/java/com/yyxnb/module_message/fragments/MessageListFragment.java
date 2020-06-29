package com.yyxnb.module_message.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.adapter.ItemDecoration;
import com.yyxnb.module_base.base.BaseFragment;
import com.yyxnb.module_message.R;
import com.yyxnb.module_message.adapter.MessageAdapter;
import com.yyxnb.module_message.config.DataConfig;
import com.yyxnb.module_message.databinding.FragmentMessageListBinding;
import com.yyxnb.skinloader.SkinManager;

import static com.yyxnb.module_base.arouter.ARouterConstant.MESSAGE_LIST_FRAGMENT;

/**
 * 消息列表.
 */
@Route(path = MESSAGE_LIST_FRAGMENT)
public class MessageListFragment extends BaseFragment {

    private FragmentMessageListBinding binding;
    private MessageAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.mRecyclerView;
        mAdapter = new MessageAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemDecoration decoration = new ItemDecoration(getContext());
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        SkinManager.get().setSkinViewResource(mRecyclerView, "line", R.color.colorLine);

    }

    @Override
    public void initViewData() {
        mAdapter.setDataItems(DataConfig.getMessageBeans());
    }
}