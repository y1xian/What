package com.yyxnb.lib_okhttp;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.yyxnb.lib_okhttp.utils.GsonUtils;
import com.yyxnb.lib_okhttp.utils.HttpCallBack;
import com.yyxnb.lib_widget.action.HandlerAction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：简单封装Okhttp
 * ================================================
 */
public class OkHttpUtils extends AbsOkHttp implements HandlerAction {

    private volatile static OkHttpUtils instance;
    // 必须要用的okhttpclient实例,在构造器中实例化保证单一实例
    private final OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Gson mGson;

    private OkHttpUtils() {
        mOkHttpClient = okHttpClient();
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

    @Override
    protected String baseUrl() {
        return "";
    }

    /**
     * 对外提供的Get方法访问
     *
     * @param url
     * @param callBack
     */
    public void get(String url, HttpCallBack callBack) {
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
     * @param params:   提交内容为表单数据
     * @param callBack
     */
    public void post(String url, Map<String, String> params, HttpCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByForm(url, params);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }

    /**
     * POST方式构建Request {Form}
     *
     * @param url
     * @param params
     * @return
     */
    private Request bulidRequestForPostByForm(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, String> entry :
                    params.entrySet()) {
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
    private void requestNetWork(Request request, HttpCallBack<Object> callBack) {
        Call call = null;
        /**
         * 处理连网逻辑，此处只处理异步操作enqueue
         */
        callBack.onLoadingBefore(request);
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                HANDLER.post(() -> callBack.onFailure(request, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (callBack.mType == String.class) {
                        // 如果想要返回字符串 直接返回就行
                        HANDLER.post(() -> callBack.onSuccess(response, resultStr));
                    } else {
                        // 需要返回解析好的javaBean集合
                        try {
                            // 此处暂时写成object，使用时返回具体的带泛型的集合
                            Object obj = mGson.fromJson(resultStr, callBack.mType);
                            HANDLER.post(() -> callBack.onSuccess(response, obj));
                        } catch (Exception e) {
                            // 解析错误时
                            HANDLER.post(() -> callBack.onError(response, e));
                        }
                    }
                } else {
                    HANDLER.post(() -> callBack.onError(response, new Exception("false")));
                }
            }
        });
    }

}