package com.yyxnb.lib_popup.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

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
