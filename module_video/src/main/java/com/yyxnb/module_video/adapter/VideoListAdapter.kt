package com.yyxnb.module_video.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.module_video.R
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.databinding.ItemVideoListLayoutBinding

class VideoListAdapter : BaseAdapter<TikTokBean>(R.layout.item_video_list_layout) {
    private var binding: ItemVideoListLayoutBinding? = null

    protected override fun bind(holder: BaseViewHolder, item: TikTokBean, position: Int) {
        binding = holder.getBinding()
        binding?.data = item
    }
}