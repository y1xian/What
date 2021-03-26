package com.yyxnb.what.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图处加载类，外界唯一调用类,直持为view,notifaication,appwidget加载图片
 */
public class ImageManager implements IImageLoder {

    private static volatile ImageManager mInstance = null;

    private IImageLoder iImageLoder;
    private Context mContext;
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private int mPlaceholderId = R.mipmap.ic_image_default;
    private int mErrorId = R.mipmap.ic_image_error;

    public static ImageManager getInstance() {
        if (null == mInstance) {
            synchronized (ImageManager.class) {
                if (null == mInstance) {
                    mInstance = new ImageManager();
                }
            }
        }
        return mInstance;
    }

    public ImageManager() {
        // 动态切换
        iImageLoder = new GlideImageLoader();
    }

    /**
     * 占位图
     * @param mPlaceholderId
     */
    public void setPlaceholderId(int mPlaceholderId) {
        this.mPlaceholderId = mPlaceholderId;
    }

    /**
     * 错误图
     * @param mErrorId
     */
    public void setErrorId(int mErrorId) {
        this.mErrorId = mErrorId;
    }

    /**
     * 全局初始化
     *
     * @param context ApplicaionContext
     */
    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void displayImage(Object url, ImageView imageView) {
        iImageLoder.displayImage(url, imageView, mPlaceholderId, mErrorId);
    }

    @Override
    public void displayImage(Object url, ImageView imageView, int placeholder, int error) {
        iImageLoder.displayImage(url, imageView, placeholder, error);
    }

    @Override
    public void displayGif(Object url, ImageView imageView) {
        iImageLoder.displayGif(url, imageView);
    }

    // 将资源ID转为Uri
    public Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + mContext.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    private RequestOptions initCommonRequestOption() {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_image_default)
                .error(R.mipmap.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .priority(Priority.NORMAL);
        return options;
    }
}
