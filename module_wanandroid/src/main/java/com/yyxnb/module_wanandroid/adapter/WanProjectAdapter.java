package com.yyxnb.module_wanandroid.adapter;

import com.yyxnb.lib_adapter.BaseAdapter;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.databinding.ItemWanProjectLayoutBinding;

public class WanProjectAdapter extends BaseAdapter<WanAriticleBean> {

    public WanProjectAdapter() {
        super(R.layout.item_wan_project_layout);
    }

    private ItemWanProjectLayoutBinding binding;

    @Override
    protected void bind(BaseViewHolder holder, WanAriticleBean bean, int position) {
        binding = holder.getBinding();
        binding.setData(bean);
    }
}
