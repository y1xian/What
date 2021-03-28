package com.yyxnb.module_widget.adapter;

import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.adapter.ItemDelegate;
import com.yyxnb.what.adapter.base.MultiItemTypeAdapter;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.bean.MainBean;


public class MainListAdapter extends MultiItemTypeAdapter<MainBean> {

    public MainListAdapter() {
        addItemDelegate(new ItemDelegate<MainBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_main_title_layout;
            }

            @Override
            public boolean isThisType(MainBean item, int position) {
                return item.type == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, MainBean mainBean, int position) {
                holder.setText(R.id.tvText, mainBean.title);
            }
        });

        addItemDelegate(new ItemDelegate<MainBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_main_list_layout;
            }

            @Override
            public boolean isThisType(MainBean item, int position) {
                return item.type == 0;
            }

            @Override
            public void convert(BaseViewHolder holder, MainBean mainBean, int position) {
                String url = "http://img0.imgtn.bdimg.com/it/u=4073821464,3431246218&fm=26&gp=0.jpg";

                holder.setText(R.id.tvText, mainBean.title);
            }
        });
    }

}
