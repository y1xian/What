package com.yyxnb.what.dialog.core;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.yyxnb.what.dialog.R;

/**
 * 消息对话框
 */
public final class MessageDialog {

    public static final class Builder
            extends UIDialog.Builder<Builder> {

        private OnListener mListener;

        private final TextView mMessageView;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.dialog_message_layout);
            mMessageView = findViewById(R.id.tv_message_message);
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public BaseDialog create() {
            // 如果内容为空就抛出异常
            if ("".equals(mMessageView.getText().toString())) {
                throw new IllegalArgumentException("Dialog message not null");
            }
            return super.create();
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_ui_confirm) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onConfirm(getDialog());
                }
            } else if (id == R.id.tv_ui_cancel) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}