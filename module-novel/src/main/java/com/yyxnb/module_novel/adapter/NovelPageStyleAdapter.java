package com.yyxnb.module_novel.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.yyxnb.lib_adapter.BaseAdapter;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.view.page.PageStyle;


public class NovelPageStyleAdapter extends BaseAdapter<Drawable> {

    private int currentChecked;

    public NovelPageStyleAdapter() {
        super(R.layout.item_novel_read_page_bg);
    }

    public void setPageStyleChecked(PageStyle pageStyle){
        currentChecked = pageStyle.ordinal();
    }

    public void setPagePosition(int pos){
        currentChecked = pos;
    }

    @Override
    protected void bind(BaseViewHolder holder, Drawable item, int position) {
        View mReadBg = holder.getView(R.id.read_bg_view);
        ImageView mIvChecked = holder.getView(R.id.read_bg_iv_checked);

        mReadBg.setBackground(item);

        mIvChecked.setVisibility(View.GONE);

        if (currentChecked == holder.getLayoutPosition()){
            mIvChecked.setVisibility(View.VISIBLE);
        }
    }
}
