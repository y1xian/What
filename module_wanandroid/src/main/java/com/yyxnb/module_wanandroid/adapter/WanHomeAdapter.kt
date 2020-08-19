package com.yyxnb.module_wanandroid.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanAriticleBean
import com.yyxnb.module_wanandroid.databinding.ItemWanHomeLayoutBinding

class WanHomeAdapter : BaseAdapter<WanAriticleBean>(R.layout.item_wan_home_layout) {

    private var binding: ItemWanHomeLayoutBinding? = null

    protected override fun bind(holder: BaseViewHolder, bean: WanAriticleBean, position: Int) {
        binding = holder.getBinding()
        binding?.data = bean
    }
}