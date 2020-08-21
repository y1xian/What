package com.yyxnb.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    protected List<T> mData = new ArrayList<>();
    private ItemDelegateManager<T> mItemDelegateManager = new ItemDelegateManager<>();
    private MultiItemTypePagedAdapter.OnItemClickListener mOnItemClickListener;

    private SparseArray<View> mHeaders = new SparseArray<>();
    private SparseArray<View> mFooters = new SparseArray<>();

    private int BASE_ITEM_TYPE_HEADER = 100000;
    private int BASE_ITEM_TYPE_FOOTER = 200000;

    public void setOnItemClickListener(MultiItemTypePagedAdapter.OnItemClickListener mOnItemClickListener) {
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

    public MultiItemTypeAdapter<T> addItemDelegate(ItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter<T> addItemDelegate(int viewType, ItemDelegate<T> itemViewDelegate) {
        mItemDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemDelegateManager() {
        return mItemDelegateManager.itemViewDelegateCount() > 0;
    }

    @Override
    public int getItemCount() {
        int itemCount = getData().size();
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
        return !useItemDelegateManager() ? super.getItemViewType(position) : mItemDelegateManager.getItemViewType(getItem(position), position);
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

    public static class SimpleOnItemClickListener implements MultiItemTypePagedAdapter.OnItemClickListener {

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
    public void setDataItems(@Nullable List<T> data) {
//        this.mData = data == null ? new ArrayList<T>() : data;
        if (data != null) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加单条数据
     */
    public void addDataItem(@IntRange(from = 0) int position, @NonNull T data) {
        mData.add(position, data);
        notifyItemInserted(position + getHeaderCount());
        compatibilityDataSizeChanged(1);
    }

    /**
     * 添加单条数据
     */
    public void addDataItem(@NonNull T data) {
        mData.add(data);
        notifyItemInserted(mData.size() + getHeaderCount());
        compatibilityDataSizeChanged(1);
    }

    /**
     * 删除单条数据
     */
    public void removeDataItem(@IntRange(from = 0) int position) {
        mData.remove(position);
        int internalPosition = position + getHeaderCount();
        notifyItemRemoved(internalPosition);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
    }

    public void removeDataItem(T t) {
        int index = mData.indexOf(t);
        if (index == -1) {
            return;
        }
        removeDataItem(index);
    }

    /**
     * 更新单条数据
     */
    public void updateDataItem(@IntRange(from = 0) int index, @NonNull T data) {
        mData.set(index, data);
        notifyItemChanged(index + getHeaderCount());
    }

    /**
     * 添加数据集
     */
    public void addDataItem(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        mData.addAll(position, newData);
        notifyItemRangeInserted(position + getHeaderCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    /**
     * 添加数据集
     */
    public void addDataItem(@NonNull Collection<? extends T> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size() + getHeaderCount(), newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    /**
     * 调换位置
     */
    public void changeDataItem(int position, T t) {
        removeDataItem(t);
        addDataItem(position, t);
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        // 不是同一个引用才清空列表
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    public List<T> getData() {
        return mData;
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        } else {
            return null;
        }
    }

}
