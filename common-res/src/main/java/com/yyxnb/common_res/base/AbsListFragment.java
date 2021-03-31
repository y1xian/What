package com.yyxnb.common_res.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.base.BasePagedViewModel;
import com.yyxnb.common_res.R;
import com.yyxnb.common_res.databinding.LayoutRefreshViewBinding;
import com.yyxnb.what.adapter.ItemDecoration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2020/12/02
 * 描    述：Paging 页
 * ================================================
 */
public abstract class AbsListFragment<T, M extends BasePagedViewModel<T>> extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    protected LayoutRefreshViewBinding binding;
    protected RecyclerView mRecyclerView;
    protected SmartRefreshLayout mRefreshLayout;
    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;
    protected M mViewModel;
    protected ItemDecoration decoration;

    @Override
    public int initLayoutResId() {
        return R.layout.layout_refresh_view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();
        binding.getRoot().setFitsSystemWindows(true);
        mRecyclerView = binding.recyclerView;
        mRefreshLayout = binding.refreshLayout;

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        adapter = getAdapter();

        //默认给列表中的Item 一个 10dp的ItemDecoration
        decoration = new ItemDecoration(getContext());
        mRecyclerView.addItemDecoration(decoration);

        genericViewModel();
    }

    private void genericViewModel() {
        //利用 子类传递的 泛型参数实例化出absViewModel 对象。
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] arguments = type.getActualTypeArguments();
        if (arguments.length > 1) {
            Type argument = arguments[1];
            Class modelClaz = ((Class) argument).asSubclass(BasePagedViewModel.class);
            mViewModel = (M) ViewModelProviders.of(this).get(modelClaz);

            //触发页面初始化数据加载的逻辑
            mViewModel.getPageData().observe(this, pagedList -> submitList(pagedList));

            //监听分页时有无更多数据,以决定是否关闭上拉加载的动画
            mViewModel.getBoundaryPageData().observe(this, hasData -> finishRefresh(hasData));
        }
    }

    public void submitList(PagedList<T> result) {
        //只有当新数据集合大于0 的时候，才调用adapter.submitList
        //否则可能会出现 页面----有数据----->被清空-----空布局
        if (result.size() > 0) {
            adapter.submitList(result);
        }
        finishRefresh(result.size() > 0);
    }

    public void finishRefresh(boolean hasData) {
        PagedList<T> currentList = adapter.getCurrentList();
        hasData = hasData || currentList != null && currentList.size() > 0;
        RefreshState state = mRefreshLayout.getState();
        if (state.isFooter && state.isOpening) {
            mRefreshLayout.finishLoadMore();
        } else if (state.isHeader && state.isOpening) {
            mRefreshLayout.finishRefresh();
        }
    }

    /**
     * 因而 我们在 onCreateView的时候 创建了 PagedListAdapter
     * 所以，如果arguments 有参数需要传递到Adapter 中，那么需要在getAdapter()方法中取出参数。
     *
     * @return
     */
    public abstract PagedListAdapter getAdapter();
}
