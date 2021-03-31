package com.yyxnb.common_res.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.adapters.ListenerUtil;

import com.yyxnb.common_base.R;
import com.yyxnb.what.image.ImageManager;

public class BindingAdapters {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    /**
     * 网络图片
     *
     * @param imageView
     * @param url
     * @param placeholder 占位
     * @param error       错误
     */
    @BindingAdapter(value = {"url", "placeholder", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int placeholder, int error) {
        if (placeholder == 0) {
            placeholder = Color.BLACK;
        }
        if (error == 0) {
            error = Color.BLACK;
        }
        ImageManager.getInstance().displayImage(url, imageView, placeholder, error);
    }

    /**
     * 防止多次点击的OnClick实现
     * <p>
     * <EditText
     * android:onTextChanged="@{(str)-> xxx.onTextChanged(str)}" />
     *
     * @param editText
     * @param listener
     */
    @BindingAdapter("android:onTextChanged")
    public static void setTextChangedListener(EditText editText, final OnTextChangedListener listener) {
        if (listener != null) {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listener.onTextChanged(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            TextWatcher oldWatcher = ListenerUtil.trackListener(editText, textWatcher, R.id.textWatcher);
            if (oldWatcher != null) {
                editText.removeTextChangedListener(oldWatcher);
            } else {
                editText.addTextChangedListener(textWatcher);
            }
        }
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}