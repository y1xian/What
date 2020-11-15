package com.yyxnb.module_video.adapter;

import com.yyxnb.lib_adapter.BaseAdapter;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.databinding.ItemVideoListLayoutBinding;

public class VideoListAdapter extends BaseAdapter<TikTokBean> {
    public VideoListAdapter() {
        super(R.layout.item_video_list_layout);
    }

    private ItemVideoListLayoutBinding binding;

    @Override
    protected void bind(BaseViewHolder holder, TikTokBean item, int position) {
        binding = holder.getBinding();
        binding.setData(item);
    }
}
