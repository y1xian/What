package com.yyxnb.what.popup.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.what.popup.R;
import com.yyxnb.what.popup.util.PopupUtils;

/**
 * Description:
 */
public class VerticalRecyclerView extends RecyclerView {
    public VerticalRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupDivider(Boolean isDark) {
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(getResources().getColor(isDark ? R.color._popup_list_dark_divider : R.color._popup_list_divider));
        drawable.setSize(10, PopupUtils.dp2px(getContext(), .4f));
        decoration.setDrawable(drawable);
        addItemDecoration(decoration);
    }

}
