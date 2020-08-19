package com.yyxnb.module_wanandroid.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.databinding.ItemWanProjectLayoutBinding

class WanProjectAdapter : BaseAdapter<WanAriticleBean>(R.layout.item_wan_project_layout) {

    private var binding: ItemWanProjectLayoutBinding? = null

    protected override fun bind(holder: BaseViewHolder, bean: WanAriticleBean, position: Int) {
        binding = holder.getBinding()
        binding?.data = bean
    }
}