package com.yyxnb.module_wanandroid.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanNavigationBean
import com.yyxnb.view.text.FlowlayoutTags
import java.util.*

class WanNavigationAdapter : BaseAdapter<WanNavigationBean>(R.layout.item_wan_tags_layout) {

    protected override fun bind(holder: BaseViewHolder, bean: WanNavigationBean, position: Int) {
        holder.setText(R.id.tvTitle, bean.name)
        val mFlowlayout: FlowlayoutTags = holder.getView(R.id.mFlowlayout)
        val tags: MutableList<String?> = ArrayList()
        for (t in bean.articles!!) {
            tags.add(t.title)
        }



        mFlowlayout.setTags(tags)
        mFlowlayout.setOnTagClickListener { tag -> toast(tag) }
    }
}