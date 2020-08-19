package com.yyxnb.common_base.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.yyxnb.common_base.R
import com.yyxnb.image_loader.ImageManager

object BindingAdapters {

    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, bitmap: Bitmap?) {
        view.setImageBitmap(bitmap)
    }

    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    /**
     * 网络图片
     *
     * @param imageView
     * @param url
     * @param placeholder 占位
     * @param error       错误
     */
    @JvmStatic
    @BindingAdapter(value = ["url", "placeholder", "error"], requireAll = false)
    fun loadImage(imageView: ImageView?, url: String?, placeholder: Int, error: Int) {
        var placeholder = placeholder
        var error = error
        if (placeholder == 0) {
            placeholder = Color.BLACK
        }
        if (error == 0) {
            error = Color.BLACK
        }
        ImageManager.getInstance().displayImage(url, imageView, placeholder, error)
    }

    /**
     * 防止多次点击的OnClick实现
     *
     *
     * <EditText android:onTextChanged="@{(str)-> xxx.onTextChanged(str)}"></EditText>
     *
     * @param editText
     * @param listener
     */
    @BindingAdapter("android:onTextChanged")
    fun setTextChangedListener(editText: EditText, listener: OnTextChangedListener?) {
        if (listener != null) {
            val textWatcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    listener.onTextChanged(s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            }
            val oldWatcher = ListenerUtil.trackListener(editText, textWatcher, R.id.textWatcher)
            if (oldWatcher != null) {
                editText.removeTextChangedListener(oldWatcher)
            } else {
                editText.addTextChangedListener(textWatcher)
            }
        }
    }

    interface OnTextChangedListener {
        fun onTextChanged(text: String?)
    }
}