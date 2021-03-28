package com.yyxnb.what.dialog.core;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import com.yyxnb.what.dialog.R;


/**
 * 项目通用 Dialog 布局封装
 */
public final class UIDialog {

    @SuppressWarnings("unchecked")
    public static class Builder<B extends UIDialog.Builder>
            extends BaseDialog.Builder<B> {

        private boolean mAutoDismiss = true;

        private final ViewGroup mContainerLayout;
        private final TextView mTitleView;

        private final TextView mCancelView;
        private final View mLineView;
        private final TextView mConfirmView;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_ui_layout);
            setAnimStyle(BaseDialog.ANIM_DEFAULT);
            setGravity(Gravity.CENTER);

            mContainerLayout = findViewById(R.id.ll_ui_container);
            mTitleView = findViewById(R.id.tv_ui_title);
            mCancelView = findViewById(R.id.tv_ui_cancel);
            mLineView = findViewById(R.id.v_ui_line);
            mConfirmView = findViewById(R.id.tv_ui_confirm);
            setOnClickListener(mCancelView, mConfirmView);
        }

        public B setCustomView(@LayoutRes int id) {
            return setCustomView(LayoutInflater.from(getContext()).inflate(id, mContainerLayout, false));
        }

        public B setCustomView(View view) {
            mContainerLayout.addView(view, 1);
            return (B) this;
        }

        public B setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }

        public B setTitle(CharSequence text) {
            mTitleView.setText(text);
            mTitleView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
            return (B) this;
        }

        public B setCancel(@StringRes int id) {
            return setCancel(getString(id));
        }

        public B setCancel(CharSequence text) {
            mCancelView.setText(text);
            mLineView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
            mCancelView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
            return (B) this;
        }

        public B setConfirm(@StringRes int id) {
            return setConfirm(getString(id));
        }

        public B setConfirm(CharSequence text) {
            mConfirmView.setText(text);
            mCancelView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
            return (B) this;
        }

        public B setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return (B) this;
        }

        public void autoDismiss() {
            if (mAutoDismiss) {
                dismiss();
            }
        }
    }
}