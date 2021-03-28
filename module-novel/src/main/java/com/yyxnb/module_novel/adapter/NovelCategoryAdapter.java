package com.yyxnb.module_novel.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.view.page.BookManager;


public class NovelCategoryAdapter extends BaseAdapter<BookChapterBean> {

    public NovelCategoryAdapter() {
        super(R.layout.item_novel_category_selected_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, BookChapterBean item, int position) {
        TextView tvChapter = holder.getView(R.id.tvName);
        tvChapter.setText(item.name);

        if (BookManager.isChapterCached(item.bookId, item.name) && !item.selected) {
            tvChapter.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (item.selected) {
            tvChapter.setTextColor(Color.parseColor("#4F5FFF"));
        } else {
            tvChapter.setTextColor(Color.parseColor("#666666"));
        }
        tvChapter.setSelected(item.selected);
    }
}
