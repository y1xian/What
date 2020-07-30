package com.yyxnb.adapter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

public class MultiItemTypePagedAdapter<T> extends PagedListAdapter<T, BaseViewHolder> {

    protected MultiItemTypePagedAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    private ItemDelegateManager<T> mItemDelegateManager = new ItemDelegateManager<>();
    private OnItemClickListener mOnItemClickListener;

    private SparseArray<View> mHeaders = new SparseArray<>();
    private SparseArray<View> mFooters = new SparseArray<>();

    private int BASE_ITEM_TYPE_HEADER = 100000;
    private int BASE_ITEM_TYPE_FOOTER = 200000;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void addHeaderView(View view) {
        //判断给View对象是否还没有处在mHeaders数组里面
        if (mHeaders.indexOfValue(view) < 0) {
            mHeaders.put(BASE_ITEM_TYPE_HEADER++, view);
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View view) {
        //判断给View对象是否还没有处在mFooters数组里面
        if (mFooters.indexOfValue(view) < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view);
            notifyDataSetChanged();
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        int index = mHeaders.indexOfValue(view);
        if (index < 0) {
            return;
        }
        mHeaders.removeAt(index);
        notifyDataSetChanged();
    }

    // 移除底部
    public void removeFooterView(View view) {
        int index = mFooters.indexOfValue(view);
        if (index < 0) {
            return;
        }
        mFooters.removeAt(index);
        notifyDataSetChanged();
    }

    public int getHeaderCount() {
        return mHeaders.size();
    }

    public int getFooterCount() {
        return mFooters.size();
    }

    public MultiItemTypePagedAdapter<T> addItemDelegate(ItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypePagedAdapter<T> addItemDelegate(int viewType, ItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemDelegateManager() {
        return mItemDelegateManager.itemViewDelegateCount() > 0;
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        return itemCount + mHeaders.size() + mFooters.size();
    }

    public int getOriginalItemCount() {
        return getItemCount() - mHeaders.size() - mFooters.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            //返回该position对应的headerview的  viewType
            return mHeaders.keyAt(position);
        }

        if (isFooterPosition(position)) {
            //footer类型的，需要计算一下它的position实际大小
            position = position - getOriginalItemCount() - mHeaders.size();
            return mFooters.keyAt(position);
        }
        position = position - mHeaders.size();
        return !useItemDelegateManager() ? super.getItemViewType(position) : (getItem(position) != null ? mItemDelegateManager.getItemViewType(getItem(position), position) : -1);
    }

    private boolean isFooterPosition(int position) {
        return position >= getOriginalItemCount() + mHeaders.size();
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaders.size();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            View view = mHeaders.get(viewType);
            return BaseViewHolder.createViewHolder(view);
        }

        if (mFooters.indexOfKey(viewType) >= 0) {
            View view = mFooters.get(viewType);
            return BaseViewHolder.createViewHolder(view);
        }

        final ItemDelegate<T> itemViewDelegate = mItemDelegateManager.getItemViewDelegate(viewType);
        final int layoutId = itemViewDelegate.layoutId();
        final BaseViewHolder holder = BaseViewHolder.createViewHolder(parent.getContext(), parent, layoutId);
        onViewHolderCreated(holder, parent, viewType);
        setListener(holder);
        return holder;
    }

    protected void onViewHolderCreated(BaseViewHolder holder, ViewGroup parent, int viewType) {
    }

    protected void convert(BaseViewHolder holder, T t) {
        mItemDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        //列表中正常类型的itemView的 position 咱们需要减去添加headerView的个数
        position = position - mHeaders.size();
        if (getItem(position) != null) {
            convert(holder, getItem(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            final GridLayoutManager.SpanSizeLookup defSpanSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mSpanSizeLookup == null) {
                        return isFixedViewType(position) ? gridManager.getSpanCount() : defSpanSizeLookup.getSpanSize(position);
                    } else {
                        return (isFixedViewType(position)) ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager,
                                position - getHeaderCount());
                    }
                }
            });
        }
    }

    protected boolean isFixedViewType(int position) {
        return isHeaderPosition(position) || isFooterPosition(position);
    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    /**
     * 点击事件
     */
    private void setListener(BaseViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                position -= getHeaderCount();
                mOnItemClickListener.onItemClick(v, holder, position);
            });

            holder.itemView.setOnLongClickListener(v -> {
                int position = holder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return false;
                }
                position -= getHeaderCount();
                return mOnItemClickListener.onItemLongClick(v, holder, position);
            });
        }
    }

    /**
     * 设置需要点击事件的子view
     */
    protected void addChildClickViewIds(BaseViewHolder holder, int... viewIds) {

        if (mOnItemClickListener != null) {
            for (int id : viewIds) {
                View childView = holder.itemView.findViewById(id);
                if (childView != null) {
                    if (!childView.isClickable()) {
                        childView.setClickable(true);
                    }
                    childView.setOnClickListener(v -> {
                        int position = holder.getAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {
                            return;
                        }
                        position -= getHeaderCount();
                        mOnItemClickListener.onItemChildClick(v, holder, position);
                    });
                }
            }
        }
    }

    /**
     * 设置需要长按点击事件的子view
     */
    protected void addChildLongClickViewIds(BaseViewHolder holder, int... viewIds) {

        if (mOnItemClickListener != null) {
            for (int id : viewIds) {
                View childView = holder.itemView.findViewById(id);
                if (childView != null) {
                    if (!childView.isLongClickable()) {
                        childView.setLongClickable(true);
                    }
                    childView.setOnLongClickListener(v -> {
                        int position = holder.getAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {
                            return false;
                        }
                        position -= getHeaderCount();
                        return mOnItemClickListener.onItemChildLongClick(v, holder, position);
                    });
                }
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        if (!isHeaderPosition(holder.getAdapterPosition()) && !isFooterPosition(holder.getAdapterPosition())) {
            this.onViewAttachedToWindow2(holder);
        }
    }

    protected void onViewAttachedToWindow2(BaseViewHolder holder) {

    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        if (!isHeaderPosition(holder.getAdapterPosition()) && !isFooterPosition(holder.getAdapterPosition())) {
            this.onViewDetachedFromWindow2(holder);
        }
    }

    protected void onViewDetachedFromWindow2(BaseViewHolder holder) {

    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(new AdapterDataObserverProxy(observer));
    }

    //如果我们先添加了headerView,而后网络数据回来了再更新到列表上
    //由于Paging在计算列表上item的位置时 并不会顾及我们有没有添加headerView，就会出现列表定位的问题
    //实际上 RecyclerView#setAdapter方法，它会给Adapter注册了一个AdapterDataObserver
    //咱么可以代理registerAdapterDataObserver()传递进来的observer。在各个方法的实现中，把headerView的个数算上，再中转出去即可
    private class AdapterDataObserverProxy extends RecyclerView.AdapterDataObserver {
        private RecyclerView.AdapterDataObserver mObserver;

        public AdapterDataObserverProxy(RecyclerView.AdapterDataObserver observer) {
            mObserver = observer;
        }

        @Override
        public void onChanged() {
            mObserver.onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mObserver.onItemRangeChanged(positionStart + mHeaders.size(), itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            mObserver.onItemRangeChanged(positionStart + mHeaders.size(), itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mObserver.onItemRangeInserted(positionStart + mHeaders.size(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mObserver.onItemRangeRemoved(positionStart + mHeaders.size(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mObserver.onItemRangeMoved(fromPosition + mHeaders.size(), toPosition + mHeaders.size(), itemCount);
        }

    }

    public interface OnItemClickListener {

        void onItemClick(View view, BaseViewHolder holder, int position);

        boolean onItemLongClick(View view, BaseViewHolder holder, int position);

        void onItemChildClick(View view, BaseViewHolder holder, int position);

        boolean onItemChildLongClick(View view, BaseViewHolder holder, int position);
    }

    public static class SimpleOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(View view, BaseViewHolder holder, int position) {

        }

        @Override
        public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
            return false;
        }

        @Override
        public void onItemChildClick(View view, BaseViewHolder holder, int position) {

        }

        @Override
        public boolean onItemChildLongClick(View view, BaseViewHolder holder, int position) {
            return false;
        }
    }

    /**
     * 新数据
     */
    @SuppressWarnings("unchecked")
    public void setDataItems(@Nullable List<T> data) {
        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        dataSource.data.addAll(data);
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    /**
     * 添加单条数据
     */
    @SuppressWarnings("unchecked")
    public void addDataItem(@IntRange(from = 0) int position, @NonNull T data) {
        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        if (position == oldData.size()) {
            dataSource.data.addAll(oldData);
            dataSource.data.add(data);
        } else {
            for (int i = 0; i < oldData.size(); i++) {
                dataSource.data.add(oldData.get(i));
                if (i == position) {
                    dataSource.data.add(i, data);
                }
            }
        }
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    /**
     * 添加单条数据
     */
    public void addDataItem(@NonNull T data) {
        addDataItem(getData().size(), data);
    }

    /**
     * 删除单条数据
     */
    public void removeDataItem(@IntRange(from = 0) int position) {
        if (position == -1) {
            return;
        }
        removeDataItem(getItem(position));
    }

    @SuppressWarnings("unchecked")
    public void removeDataItem(T t) {

        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        for (T it : oldData) {
            if (it != t) {
                dataSource.data.add(it);
            }
        }
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    /**
     * 更新单条数据
     */
    @SuppressWarnings("unchecked")
    public void updateDataItem(@IntRange(from = 0) int index, @NonNull T data) {
        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        for (int i = 0; i < oldData.size(); i++) {
            if (i != index) {
                dataSource.data.add(oldData.get(i));
            }
            if (i == index) {
                dataSource.data.add(i, data);
            }
        }
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    /**
     * 添加数据集
     */
    @SuppressWarnings("unchecked")
    public void addDataItem(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        if (position == oldData.size()) {
            dataSource.data.addAll(oldData);
            dataSource.data.addAll(newData);
        } else {
            for (int i = 0; i < oldData.size(); i++) {
                dataSource.data.add(oldData.get(i));
                if (i == position) {
                    dataSource.data.addAll(i, newData);
                }
            }
        }
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    /**
     * 添加数据集
     */
    public void addDataItem(@NonNull Collection<? extends T> newData) {
        addDataItem(getData().size(), newData);
    }

    /**
     * 调换位置
     */
    @SuppressWarnings("unchecked")
    public void changeDataItem(int index, T t) {
        PagedList<T> oldData = getData();
        MutablePageKeyedDataSource dataSource = new MutablePageKeyedDataSource<T>();
        for (int i = 0; i < oldData.size(); i++) {
            if (i == index) {
                dataSource.data.add(i, t);
            }
            if (i != index) {
                dataSource.data.add(oldData.get(i));
            }
        }
        PagedList pagedList = dataSource.buildNewPagedList(oldData.getConfig());
        submitList(pagedList);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public PagedList<T> getData() {
        return getCurrentList();
    }

}
