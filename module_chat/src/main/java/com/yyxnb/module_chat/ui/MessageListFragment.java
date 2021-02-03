package com.yyxnb.module_chat.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.lib_adapter.ItemDecoration;
import com.yyxnb.lib_skinloader.SkinManager;
import com.yyxnb.module_chat.R;
import com.yyxnb.module_chat.adapter.MessageAdapter;
import com.yyxnb.module_chat.config.DataConfig;
import com.yyxnb.module_chat.databinding.FragmentMessageListBinding;

import static com.yyxnb.common_res.arouter.ARouterConstant.CHAT_LIST_FRAGMENT;

/**
 * 消息列表.
 */
@Route(path = CHAT_LIST_FRAGMENT)
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
        mRecyclerView = binding.rvContent;
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