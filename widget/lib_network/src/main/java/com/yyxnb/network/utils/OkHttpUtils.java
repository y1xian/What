package com.yyxnb.network.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装Okhttp
 *
 * @author yyx
 */
public class OkHttpUtils {
    /**
     * 网络访问要求singleton
     */
    private volatile static OkHttpUtils instance;
    // 必须要用的okhttpclient实例,在构造器中实例化保证单一实例
    private OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.
            parse("application/json; charset=utf-8");
    private Handler mHandler;
    private Gson mGson;

    //仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
    public static final int CACHE_ONLY = 1;
    //先访问缓存，同时发起网络的请求，成功后缓存到本地
    public static final int CACHE_FIRST = 2;
    //仅仅只访问服务器，不存任何存储
    public static final int NET_ONLY = 3;
    //先访问网络，成功后缓存到本地
    public static final int NET_CACHE = 4;
    private String cacheKey;
    //private Class mClaz;
    private int mCacheStrategy = CACHE_FIRST;

    @IntDef({CACHE_ONLY, CACHE_FIRST, NET_CACHE, NET_ONLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheStrategy {
    }

    private OkHttpUtils() {
        /**
         * okHttp3中超时方法移植到Builder中
         */
        mOkHttpClient = (new OkHttpClient()).newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mHandler = new Handler(Looper.getMainLooper());
        mGson = GsonUtils.getGson();
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 对外提供的Get方法访问
     *
     * @param url
     * @param callBack
     */
    public void get(String url, MyCallBack callBack) {
        /**
         * 通过url和GET方式构建Request
         */
        Request request = bulidRequestForGet(url);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param parms:   提交内容为表单数据
     * @param callBack
     */
    public void postWithFormData(String url, Map<String, String> parms, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByForm(url, parms);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param json:    提交内容为json数据
     * @param callBack
     */
    public void postWithJson(String url, String json, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByJson(url, json);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * POST方式构建Request {json}
     *
     * @param url
     * @param json
     * @return
     */
    private Request bulidRequestForPostByJson(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    /**
     * POST方式构建Request {Form}
     *
     * @param url
     * @param parms
     * @return
     */
    private Request bulidRequestForPostByForm(String url, Map<String, String> parms) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parms != null) {
            for (Map.Entry<String, String> entry :
                    parms.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    /**
     * GET方式构建Request
     *
     * @param url
     * @return
     */
    private Request bulidRequestForGet(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    /**
     * 请求网络的逻辑
     */
    private void requestNetWork(Request request, MyCallBack<Object> callBack) {
        /**
         * 处理连网逻辑，此处只处理异步操作enqueue
         */
        callBack.onLoadingBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(() -> callBack.onFailure(request, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (callBack.mType == String.class) {
                        // 如果想要返回字符串 直接返回就行
                        mHandler.post(() -> callBack.onSuccess(response, resultStr));
                    } else {
                        // 需要返回解析好的javaBean集合
                        try {
                            // 此处暂时写成object，使用时返回具体的带泛型的集合
                            Object obj = mGson.fromJson(resultStr, callBack.mType);
                            mHandler.post(() -> callBack.onSuccess(response, obj));
                        } catch (Exception e) {
                            // 解析错误时
                            mHandler.post(() -> callBack.onError(response, e));
                        }
                    }
                } else {
                    mHandler.post(() -> callBack.onError(response, new Exception("false")));
                }
            }
        });
    }

}