package com.yyxnb.module_novel.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.yyxnb.lib_adapter.base.BaseAdapter;
import com.yyxnb.lib_adapter.base.BaseViewHolder;
import com.yyxnb.lib_image_loader.ImageManager;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.bean.BookShelfBean;

public class BookShelfAdapter extends BaseAdapter<BookShelfBean> {

    public BookShelfAdapter() {
        super(R.layout.item_book_shelf_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, BookShelfBean item, int position) {
        CardView mCardView = holder.getView(R.id.mCardView);
        ConstraintLayout mConstraintLayout = holder.getView(R.id.mConstraintLayout);


//        if ("0".equals(item.getBookShelfId())) {
//            mConstraintLayout.setVisibility(View.GONE);
//            mCardView.setVisibility(View.VISIBLE);
//        } else {
//            ImageView ivUpdate = holder.getView(R.id.ivUpdate);
//            if (Integer.valueOf(item.getIndexId()) > Integer.valueOf(item.getHistoryIndex())) {
//                ivUpdate.setVisibility(View.VISIBLE);
//            } else {
//                ivUpdate.setVisibility(View.GONE);
//            }

        mConstraintLayout.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.GONE);

        ImageView ivHead = holder.getView(R.id.ivHead);

//            Glide.with(ivHead.getContext()).load(item.getCoverPath()).error(R.drawable.ic_error).into(ivHead);
        ImageManager.getInstance().displayImage(item.bookInfoBean.picture, ivHead);

        holder.setText(R.id.tvTitle, item.bookInfoBean.title);
        holder.setText(R.id.tvAuthor, item.bookInfoBean.author);
//        }
    }
}
