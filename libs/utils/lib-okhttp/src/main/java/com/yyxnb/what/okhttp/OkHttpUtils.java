package com.yyxnb.what.okhttp;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yyxnb.what.core.UITask;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.what.okhttp.utils.HttpCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：简单封装OkHttp
 * ================================================
 */
public class OkHttpUtils extends AbsOkHttp {

    private volatile static OkHttpUtils instance;
    // 必须要用的okhttpclient实例,在构造器中实例化保证单一实例
    private final OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Gson mGson;

    private List<Cookie> cookies;
    private Map<String, String> headers = new HashMap<>();

    public OkHttpUtils setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    private OkHttpUtils() {
        mOkHttpClient = okHttpClient();
        mGson = GsonUtils.getGson();
        cookies = new ArrayList<>();
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

    @Override
    protected Map<String, String> header() {
        return headers;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public void clearCookies() {
        this.cookies = new ArrayList<>();
        this.cookieStore = new HashMap<>();
    }

    /**
     * 对外提供的Get方法访问
     *
     * @param url
     * @param callBack
     */
    public OkHttpUtils get(String url, HttpCallBack callBack) {
        /**
         * 通过url和GET方式构建Request
         */
        Request request = bulidRequestForGet(url);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
        return this;
    }

    @Override
    protected CookieJar cookieJar() {
        return new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param params   提交内容为表单数据
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
     * 适用于需要传参数和json对象的接口
     * Post 异步请求
     *
     * @param url        地址
     * @param map        提交内容为表单数据
     * @param jsonString json字符串
     * @param callback   异步回调
     */
    public void asyPostJson(String url, Map<String, String> map, String jsonString, HttpCallBack callback) {
        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        Log.e("======>>", url + " 请求json：" + jsonString);
        String urls = url + "?";
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry entry : entries) {
                String key = valueOf(entry.getKey());
                String value = valueOf(entry.getValue());
                urls = urls + "&" + key + "=" + value;
            }
        }
        Log.e("", "============URL============" + url + ":" + urls);
        Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        requestNetWork(request, callback);
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
                UITask.run(() -> callBack.onFailure(request, e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    if (callBack.mType == String.class) {
                        // 如果想要返回字符串 直接返回就行
                        UITask.run(() -> callBack.onSuccess(response, resultStr));
                    } else {
                        // 需要返回解析好的javaBean集合
                        try {
                            // 此处暂时写成object，使用时返回具体的带泛型的集合
                            Object obj = mGson.fromJson(resultStr, callBack.mType);
                            UITask.run(() -> callBack.onSuccess(response, obj));
                        } catch (Exception e) {
                            // 解析错误时
                            UITask.run(() -> callBack.onError(response, e));
                        }
                    }
                } else {
                    UITask.run(() -> callBack.onError(response, new Exception("false")));
                }
            }
        });
    }

}