package com.yyxnb.module_novel.adapter;

import com.yyxnb.adapter.BaseAdapter;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.module_novel.databinding.ItemBookHomeLayoutBinding;

public class BookHomeAdapter extends BaseAdapter<BookInfoBean> {

    public BookHomeAdapter() {
        super(R.layout.item_book_home_layout);
    }

    private ItemBookHomeLayoutBinding binding;

    @Override
    protected void bind(BaseViewHolder holder, BookInfoBean item, int position) {
        binding = holder.getBinding();
        binding.setData(item);
    }
}
