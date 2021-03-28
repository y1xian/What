package com.yyxnb.what.popup.impl;

import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yyxnb.what.popup.PopupManager;
import com.yyxnb.what.popup.R;
import com.yyxnb.what.popup.code.CenterPopupView;


/**
 * Description: 加载对话框
 */
public class LoadingPopupView extends CenterPopupView {
    private TextView tv_title;

    /**
     *
     * @param context
     * @param bindLayoutId  layoutId 如果要显示标题，则要求必须有id为tv_title的TextView，否则无任何要求
     */
    public LoadingPopupView(@NonNull Context context, int bindLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        addInnerContent();
    }

    @Override
    protected int initLayoutResId() {
        return bindLayoutId != 0 ? bindLayoutId : R.layout._popup_center_impl_loading;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_title = findViewById(R.id.tvTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getPopupImplView().setElevation(10f);
        }
        setup();
    }
    protected void setup() {
        if (title != null && title.length()!=0 && tv_title != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (tv_title.getText().length() != 0) {
                        TransitionManager.beginDelayedTransition((ViewGroup) tv_title.getParent(), new TransitionSet()
                                .setDuration(PopupManager.getAnimationDuration())
                                .addTransition(new ChangeBounds()));
                    }
                    tv_title.setVisibility(VISIBLE);
                    tv_title.setText(title);
                }
            });
        }
    }

    private CharSequence title;

    public LoadingPopupView setTitle(CharSequence title) {
        this.title = title;
        setup();
        return this;
    }
}
