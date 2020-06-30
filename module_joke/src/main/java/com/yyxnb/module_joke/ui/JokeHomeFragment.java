package com.yyxnb.module_joke.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_joke.R;
import com.yyxnb.module_joke.databinding.FragmentJokeHomeBinding;

import static com.yyxnb.common_base.arouter.ARouterConstant.JOKE_HOME_FRAGMENT;

/**
 * joke 首页.
 */
@Route(path = JOKE_HOME_FRAGMENT)
public class JokeHomeFragment extends BaseFragment {

    private FragmentJokeHomeBinding binding;
    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.mRecyclerView;

//        mAdapter = new VideoListAdapter();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ItemDecoration decoration = new ItemDecoration(getContext());
//        decoration.setDividerColor(Color.WHITE)
//                .setDividerHeight(1)
//                .setDividerWidth(1)
//                .setDrawBorderTopAndBottom(true)
//                .setDrawBorderLeftAndRight(true);
//        mRecyclerView.addItemDecoration(decoration);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initViewData() {

    }
}