package com.yyxnb.module_main.adapter;

import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.ItemDelegate;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.bean.MainHomeBean;

public class MainHomeAdapter extends MultiItemTypeAdapter<MainHomeBean> {

    public MainHomeAdapter() {
//        super(new ItemDiffCallback<>());
        super();
        addItemDelegate(new ItemDelegate<MainHomeBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_home_type_1_layout;
            }

            @Override
            public boolean isThisType(MainHomeBean item, int position) {
                return item.type == 1;
            }

            @Override
            public void convert(BaseViewHolder holder, MainHomeBean mainHomeBean, int position) {

            }
        });
        addItemDelegate(new ItemDelegate<MainHomeBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_home_type_2_layout;
            }

            @Override
            public boolean isThisType(MainHomeBean item, int position) {
                return item.type == 2;
            }

            @Override
            public void convert(BaseViewHolder holder, MainHomeBean mainHomeBean, int position) {

            }
        });
        addItemDelegate(new ItemDelegate<MainHomeBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_home_type_3_layout;
            }

            @Override
            public boolean isThisType(MainHomeBean item, int position) {
                return item.type == 3;
            }

            @Override
            public void convert(BaseViewHolder holder, MainHomeBean mainHomeBean, int position) {

            }
        });
    }
}
