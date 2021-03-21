package com.yyxnb.what.download.download;

import com.yyxnb.what.okhttp.AbsOkHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadHelper extends AbsOkHttp {

    private static volatile DownloadHelper mInstance = null;

    private DownloadHelper() {
    }

    public static DownloadHelper getInstance() {
        if (null == mInstance) {
            synchronized (DownloadHelper.class) {
                if (null == mInstance) {
                    mInstance = new DownloadHelper();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected String baseUrl() {
        return "";
    }

    /**
     * @param url        下载链接
     * @param startIndex 下载起始位置
     * @param endIndex   结束为止
     * @param callback   回调
     * @throws IOException
     */
    public void downloadFileByRange(String url, long startIndex, long endIndex, Callback callback) throws IOException {
        // 创建一个Request
        // 设置分段下载的头信息。 Range:做分段数据请求,断点续传指示下载的区间。格式: Range bytes=0-1024或者bytes:0-1024
        Request request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + endIndex)
                .url(url)
                .build();
        doAsync(request, callback);
    }

    public void getContentLength(String url, Callback callback) throws IOException {
        // 创建一个Request
        Request request = new Request.Builder()
                .url(url)
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

    /**
     * 同步请求
     */
    private Response doSync(Request request) throws IOException {

        //创建请求会话
        Call call = okHttpClient().newCall(request);
        //同步执行会话请求
        return call.execute();
    }


}
