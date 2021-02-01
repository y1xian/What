package com.yyxnb.lib_adapter;

public interface ItemDelegate<T> {
    /**
     * 布局文件
     */
    int layoutId();

    /**
     * 类型区分
     */
    boolean isThisType(T item, int position);

    /**
     * 数据处理
     */
    void convert(BaseViewHolder holder, T item, int position);
}
