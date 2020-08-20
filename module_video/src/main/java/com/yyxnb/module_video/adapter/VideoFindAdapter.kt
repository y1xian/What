package com.yyxnb.module_video.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.module_video.R
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.databinding.ItemVideoFindLayoutBinding

class VideoFindAdapter : BaseAdapter<TikTokBean>(R.layout.item_video_find_layout) {
    private var binding: ItemVideoFindLayoutBinding? = null

    protected override fun bind(holder: BaseViewHolder, item: TikTokBean, position: Int) {
        binding = holder.getBinding()
        binding?.data = item
        //        ImageView ivThumb = holder.getView(R.id.ivThumb);
//        Glide.with(ivThumb.getContext())
//                .load(item.coverUrl)
//                .into(ivThumb);
//        holder.setText(R.id.tvText,item.title);
    }
}