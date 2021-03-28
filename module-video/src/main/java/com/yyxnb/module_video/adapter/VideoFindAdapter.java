package com.yyxnb.module_video.adapter;

import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.databinding.ItemVideoFindLayoutBinding;

public class VideoFindAdapter extends BaseAdapter<TikTokBean> {
    public VideoFindAdapter() {
        super(R.layout.item_video_find_layout);
    }

    private ItemVideoFindLayoutBinding binding;

    @Override
    protected void bind(BaseViewHolder holder, TikTokBean item, int position) {
        binding = holder.getBinding();
        binding.setData(item);
//        ImageView ivThumb = holder.getView(R.id.ivThumb);
//        Glide.with(ivThumb.getContext())
//                .load(item.coverUrl)
//                .into(ivThumb);
//        holder.setText(R.id.tvText,item.title);
    }
}
