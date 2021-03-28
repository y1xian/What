package com.yyxnb.what.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.what.core.interfaces.IData;

import java.util.List;

/**
 * 用来判断 新旧Item是否相等
 */
public class DiffCallBack<T extends IData<T>> extends DiffUtil.Callback {
    private List<T> oldDatas;
    private List<T> newDatas;

    public DiffCallBack(List<T> oldDatas, List<T> newDatas) {
        this.oldDatas = oldDatas;
        this.newDatas = newDatas;
    }

    public static int getListSize(List list) {
        return list == null ? 0 : list.size();
    }

    /**
     * 旧数据的size
     */
    @Override
    public int getOldListSize() {
        return getListSize(oldDatas);
    }


    /**
     * 新数据的size
     */
    @Override
    public int getNewListSize() {
        return getListSize(newDatas);
    }

    /**
     * 当{@link #areItemsTheSame(int, int)} 返回true，且{@link #areContentsTheSame(int, int)} 返回false时，DiffUtils会回调此方法，
     * 去得到这个Item（有哪些）改变的payload。
     * 例如，如果你用RecyclerView配合DiffUtils，你可以返回  这个Item改变的那些字段，
     *
     * {@link RecyclerView.ItemAnimator ItemAnimator} 可以用那些信息去执行正确的动画
     *
     * 返回 一个 代表着新老item的改变内容的 payload对象
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    /**
     * 这个方法自由定制 ，
     * 在对比数据的时候会被调用
     * 返回 true 被判断为同一个item
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame(oldDatas.get(oldItemPosition), newDatas.get(newItemPosition));
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     * 返回true 就证明内容相同
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame(oldDatas.get(oldItemPosition), newDatas.get(newItemPosition));
    }

    /**
     * 重写此方法, 判断数据是否相等,
     * 如果item不相同, 会先调用 notifyItemRangeRemoved, 再调用 notifyItemRangeInserted
     */
    public boolean areItemsTheSame(@NonNull T oldData, @NonNull T newData) {
        Class<?> oldClass = oldData.getClass();
        Class<?> newClass = newData.getClass();
        if (oldClass.isAssignableFrom(newClass) || newClass.isAssignableFrom(oldClass)) {
            return true;
        }
        return TextUtils.equals(oldClass.getSimpleName(), newClass.getSimpleName());
    }

    /**
     * 重写此方法, 判断内容是否相等,
     * 如果内容不相等, 会调用notifyItemRangeChanged
     */
    public boolean areContentsTheSame(@NonNull T oldData, @NonNull T newData) {
        Class<?> oldClass = oldData.getClass();
        Class<?> newClass = newData.getClass();
        if (oldClass.isAssignableFrom(newClass) ||
                newClass.isAssignableFrom(oldClass) ||
                TextUtils.equals(oldClass.getSimpleName(), newClass.getSimpleName())) {
            return oldData.equals(newData);
        }
        return false;
    }
}