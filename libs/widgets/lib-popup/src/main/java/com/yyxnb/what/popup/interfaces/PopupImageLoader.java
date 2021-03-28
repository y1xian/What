package com.yyxnb.what.popup.interfaces;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;

public interface PopupImageLoader {
    void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView);

    /**
     * 获取图片对应的文件
     * @param context
     * @param uri
     * @return
     */
    File getImageFile(@NonNull Context context, @NonNull Object uri);
}
