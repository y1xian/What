package com.yyxnb.what.download.upload;

import com.yyxnb.what.okhttp.AbsOkHttp;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UploadHelper extends AbsOkHttp {

    private static final String TAG = "UploadHelper";
    private static volatile UploadHelper mInstance = null;

    private UploadHelper() {
    }

    public static UploadHelper getInstance() {
        if (null == mInstance) {
            synchronized (UploadHelper.class) {
                if (null == mInstance) {
                    mInstance = new UploadHelper();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected String baseUrl() {
        return null;
    }


    public void upload(String url, List<String> fileNames, RequestBody requestBody, Callback callback) throws IOException {

    }


    /**
     * 文件上传
     *
     * @param url
     * @param requestBody
     * @param callback
     * @throws IOException
     */
    public void upload(String url, RequestBody requestBody, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        doAsync(request, callback);
    }

    /**
     * 异步请求
     */
    private void doAsync(Request request, Callback callback) throws IOException {
        //创建请求会话
        Call call = okHttpClient().newCall(request);
        //异步执行会话请求
        call.enqueue(callback);
    }
}
