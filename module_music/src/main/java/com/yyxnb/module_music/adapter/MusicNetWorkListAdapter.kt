package com.yyxnb.module_music.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.module_music.R
import com.yyxnb.module_music.bean.MusicBean

class MusicNetWorkListAdapter : BaseAdapter<MusicBean>(R.layout.item_music_home_list) {

    protected override fun bind(holder: BaseViewHolder, item: MusicBean, position: Int) {
        holder.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_anchor, item.author)
    }
}