package com.yyxnb.module_wanandroid.adapter;

import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.databinding.ItemWanHomeLayoutBinding;

public class WanHomeAdapter extends BaseAdapter<WanAriticleBean> {

    public WanHomeAdapter() {
        super(R.layout.item_wan_home_layout);
    }

    private ItemWanHomeLayoutBinding binding;

    @Override
    protected void bind(BaseViewHolder holder, WanAriticleBean bean, int position) {
        binding = holder.getBinding();
        binding.setData(bean);
    }
}
