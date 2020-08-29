package com.yyxnb.module_novel.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.adapter.BookHomeAdapter;
import com.yyxnb.module_novel.config.DataConfig;
import com.yyxnb.module_novel.databinding.FragmentBookHomeBinding;
import com.yyxnb.module_novel.db.NovelDatabase;

/**
 * 小说 - 首页 精选.
 */
@BindRes
public class BookHomeFragment extends BaseFragment {

    private BookHomeAdapter mAdapter;
    private FragmentBookHomeBinding binding;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_book_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.mRecyclerView;

        mAdapter = new BookHomeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
//                toast(mAdapter.getItem(position).id +"");
                Bundle bundle = initArguments();
                bundle.putInt("bookId",mAdapter.getItem(position).bookId);
                startFragment(BookDetailsFragment.newInstance(mAdapter.getItem(position).bookId));
            }
        });
    }

    @Override
    public void initObservable() {
        mAdapter.setDataItems(DataConfig.getMainBeans());
        NovelDatabase.getInstance().bookHomeDao().insertItems(DataConfig.getMainBeans());
    }
}