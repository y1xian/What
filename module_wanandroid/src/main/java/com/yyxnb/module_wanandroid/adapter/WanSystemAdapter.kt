package com.yyxnb.module_wanandroid.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanSystemBean
import com.yyxnb.view.text.FlowlayoutTags
import java.util.*

class WanSystemAdapter : BaseAdapter<WanSystemBean>(R.layout.item_wan_tags_layout) {

    protected override fun bind(holder: BaseViewHolder, bean: WanSystemBean, position: Int) {
        holder.setText(R.id.tvTitle, bean.name)
        val mFlowlayout: FlowlayoutTags = holder.getView(R.id.mFlowlayout)
        val tags: MutableList<String> = ArrayList()
        for (t in bean.children!!) {
            tags.add(t.name)
        }
        mFlowlayout.setTags(tags)
        mFlowlayout.setOnTagClickListener { tag -> toast(tag) }
    }
}