package com.yyxnb.module_wanandroid.adapter;

import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.core.ToastUtils;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.bean.WanSystemBean;
import com.yyxnb.what.view.text.FlowlayoutTags;

import java.util.ArrayList;
import java.util.List;

public class WanSystemAdapter extends BaseAdapter<WanSystemBean> {

    public WanSystemAdapter() {
        super(R.layout.item_wan_tags_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, WanSystemBean bean, int position) {
        holder.setText(R.id.tvTitle, bean.name);
        FlowlayoutTags mFlowlayout = holder.getView(R.id.flowLayout);
        List<String> tags = new ArrayList<>();
        for (WanClassifyBean classifyBean : bean.children) {
            tags.add(classifyBean.name);
        }

        mFlowlayout.setTags(tags);

        mFlowlayout.setOnTagClickListener(new FlowlayoutTags.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                ToastUtils.normal(tag);
            }
        });
    }
}
