package com.yyxnb.module_wanandroid.adapter;

import com.yyxnb.lib_adapter.BaseAdapter;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.lib_common.utils.ToastUtils;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanAriticleBean;
import com.yyxnb.module_wanandroid.bean.WanNavigationBean;
import com.yyxnb.lib_view.text.FlowlayoutTags;

import java.util.ArrayList;
import java.util.List;

public class WanNavigationAdapter extends BaseAdapter<WanNavigationBean> {

    public WanNavigationAdapter() {
        super(R.layout.item_wan_tags_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, WanNavigationBean bean, int position) {
        holder.setText(R.id.tvTitle, bean.name);
        FlowlayoutTags mFlowlayout = holder.getView(R.id.mFlowlayout);
        List<String> tags = new ArrayList<>();
        for (WanAriticleBean ariticleBean : bean.articles) {
            tags.add(ariticleBean.title);
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
