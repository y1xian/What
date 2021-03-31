package com.yyxnb.module_main.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.noober.background.drawable.DrawableCreator;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.bean.MainHomeBean;
import com.yyxnb.what.adapter.ItemDelegate;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.adapter.base.MultiItemTypeAdapter;
import com.yyxnb.what.core.DpUtils;

public class MainHomeAdapter extends MultiItemTypeAdapter<MainHomeBean> {

    public MainHomeAdapter() {
//        super(new ItemDiffCallback<>());
        super();
        // 搜索
        addItemDelegate(new ItemDelegate<MainHomeBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_home_type_search_layout;
            }

            @Override
            public boolean isThisType(MainHomeBean item, int position) {
                return item.type == 20;
            }

            @Override
            public void convert(BaseViewHolder holder, MainHomeBean mainHomeBean, int position) {
            }
        });
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
                ConstraintLayout mLayout = holder.getView(R.id.clContent);
                TextView tvTitle = holder.getView(R.id.tvTitle);
                setView(mLayout, tvTitle, mainHomeBean);
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
                ConstraintLayout mLayout = holder.getView(R.id.clContent);
                TextView tvTitle = holder.getView(R.id.tvTitle);
                setView(mLayout, tvTitle, mainHomeBean);
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
                ConstraintLayout mLayout = holder.getView(R.id.clContent);
                TextView tvTitle = holder.getView(R.id.tvTitle);
                setView(mLayout, tvTitle, mainHomeBean);
            }
        });
    }

    // 统一设置
    private void setView(ConstraintLayout mLayout, TextView tvTitle, MainHomeBean bean) {

        if (!bean.color.isEmpty()) {
            Drawable drawable = new DrawableCreator.Builder()
                    .setSolidColor(Color.parseColor(bean.color))
                    .setCornersRadius(DpUtils.dp2px(mLayout.getContext(),10))
                    .build();
            mLayout.setBackground(drawable);
        }
        tvTitle.setText(bean.title);
    }
}
