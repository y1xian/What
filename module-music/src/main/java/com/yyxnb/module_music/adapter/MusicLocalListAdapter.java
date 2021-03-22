package com.yyxnb.module_music.adapter;


import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.bean.MusicLocalBean;

public class MusicLocalListAdapter extends BaseAdapter<MusicLocalBean> {

    public MusicLocalListAdapter() {
        super(R.layout.item_music_home_list);
    }

    @Override
    protected void bind(BaseViewHolder holder, MusicLocalBean item, int position) {
        holder.setText(R.id.tvTitle, item.title)
                .setText(R.id.tv_anchor, item.author);
    }


}
