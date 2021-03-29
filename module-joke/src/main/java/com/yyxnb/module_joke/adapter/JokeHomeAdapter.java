package com.yyxnb.module_joke.adapter;

import android.widget.FrameLayout;

import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.adapter.ItemDelegate;
import com.yyxnb.what.adapter.base.MultiItemTypeAdapter;
import com.yyxnb.module_joke.R;
import com.yyxnb.module_joke.bean.TikTokBean;
import com.yyxnb.module_joke.databinding.LayoutHomeTypeImageBinding;
import com.yyxnb.module_joke.databinding.LayoutHomeTypeVideoBinding;

public class JokeHomeAdapter extends MultiItemTypeAdapter<TikTokBean> {

    public FrameLayout mPlayerContainer;
    public int mPosition;

    public JokeHomeAdapter() {
        super();
        addItemDelegate(new ItemDelegate<TikTokBean>() {

            private LayoutHomeTypeVideoBinding binding;


            @Override
            public int layoutId() {
                return R.layout.layout_home_type_video;
            }

            @Override
            public boolean isThisType(TikTokBean item, int position) {
                return item.type == 0;
            }

            @Override
            public void convert(BaseViewHolder holder, TikTokBean tikTokBean, int position) {
                binding = holder.getBinding();
                binding.setData(tikTokBean);
                mPosition = position;
            }
        });
        addItemDelegate(new ItemDelegate<TikTokBean>() {

            private LayoutHomeTypeImageBinding binding;

            @Override
            public int layoutId() {
                return R.layout.layout_home_type_image;
            }

            @Override
            public boolean isThisType(TikTokBean item, int position) {
                return item.type == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, TikTokBean tikTokBean, int position) {
                binding = holder.getBinding();
                binding.setData(tikTokBean);

                mPosition = position;
                binding.ivCover.setImageUrl(tikTokBean.coverUrl);
            }
        });
    }
}
