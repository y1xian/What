package com.yyxnb.module_widget.view.dialog;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.yyxnb.lib_dialog.core.BaseDialog;
import com.yyxnb.module_widget.R;


/**
 * 等待加载对话框
 */
public final class WaitDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private final TextView mMessageView;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_wait_layout);
            setAnimStyle(BaseDialog.ANIM_TOAST);
            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_wait_message);
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }
    }
}