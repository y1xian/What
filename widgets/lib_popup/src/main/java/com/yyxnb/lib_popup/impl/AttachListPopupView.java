package com.yyxnb.lib_popup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yyxnb.lib_adapter.BaseAdapter;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_popup.R;
import com.yyxnb.lib_popup.code.AttachPopupView;
import com.yyxnb.lib_popup.interfaces.OnSelectListener;
import com.yyxnb.lib_popup.widget.VerticalRecyclerView;

import java.util.Arrays;

/**
 * Description: Attach类型的列表弹窗
 */
public class AttachListPopupView extends AttachPopupView {
    RecyclerView recyclerView;
    protected int bindLayoutId;
    protected int bindItemLayoutId;

    /**
     * @param context
     * @param bindLayoutId     layoutId 要求layoutId中必须有一个id为recyclerView的RecyclerView
     * @param bindItemLayoutId itemLayoutId 条目的布局id，要求布局中必须有id为iv_image的ImageView，和id为tv_text的TextView
     */
    public AttachListPopupView(@NonNull Context context, int bindLayoutId, int bindItemLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        this.bindItemLayoutId = bindItemLayoutId;
        addInnerContent();
    }

    @Override
    protected int initLayoutResId() {
        return bindLayoutId == 0 ? R.layout._popup_attach_impl_list : bindLayoutId;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        recyclerView = findViewById(R.id.rv_content);
        if (recyclerView instanceof VerticalRecyclerView) {
            ((VerticalRecyclerView) recyclerView).setupDivider(popupInfo.isDarkTheme);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        final BaseAdapter<String> adapter = new BaseAdapter<String>(bindItemLayoutId == 0 ? R.layout._popup_adapter_text : bindItemLayoutId) {
            @Override
            protected void bind(@NonNull BaseViewHolder holder, @NonNull String s, int position) {
                holder.setText(R.id.tv_text, s);
                if (iconIds != null && iconIds.length > position) {
                    holder.getView(R.id.iv_image).setVisibility(VISIBLE);
                    holder.getView(R.id.iv_image).setBackgroundResource(iconIds[position]);
                } else {
                    holder.getView(R.id.iv_image).setVisibility(GONE);
                }
//                holder.getView(R.id.mDivider).setVisibility(GONE);
                View check = holder.getView(R.id.check_view);
                if (check != null) {
                    check.setVisibility(GONE);
                }

                if (bindItemLayoutId == 0 && popupInfo.isDarkTheme) {
                    holder.<TextView>getView(R.id.tv_text).setTextColor(getResources().getColor(R.color._popup_white_color));
                }
            }
        };
        adapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                if (selectListener != null) {
                    selectListener.onSelect(position, adapter.getData().get(position));
                }
                if (popupInfo.autoDismiss) {
                    dismiss();
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setDataItems(Arrays.asList(data));


        if (bindLayoutId == 0 && popupInfo.isDarkTheme) {
            applyDarkTheme();
        }
    }

    @Override
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        recyclerView.setBackgroundColor(getResources().getColor(R.color._popup_dark_color));
    }

    String[] data;
    int[] iconIds;

    public AttachListPopupView setStringData(String[] data, int[] iconIds) {
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }

//    public AttachListPopupView setOffsetXAndY(int offsetX, int offsetY) {
//        this.defaultOffsetX += offsetX;
//        this.defaultOffsetY += offsetY;
//        return this;
//    }

    private OnSelectListener selectListener;

    public AttachListPopupView setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }
}
