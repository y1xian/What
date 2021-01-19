package com.yyxnb.lib_file.download;

import android.text.TextUtils;

import com.yyxnb.lib_okhttp.AbsOkHttp;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadHelper extends AbsOkHttp {

    @Override
    protected String baseUrl() {
        return "";
    }
    //

    public void downloadingFile(String url, String saveFileDir, String saveFileName,long completedSize) {

        Request request = null;
        Response response = null;
        OkHttpClient httpClient = okHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Connection", "close");

        RandomAccessFile accessFile = null;
        InputStream inputStream = null;
        BufferedInputStream bis = null;

        try {
            request = builder.build();
            response = httpClient.newCall(request).execute();

            ResponseBody responseBody = response.body();

            int length;
//            long completedSize = 0;

            accessFile = new RandomAccessFile(saveFileDir + saveFileName, "rwd");

            //服务器不支持断点下载时重新下载
            if (TextUtils.isEmpty(response.header("Content-Range"))) {
                completedSize = 0L;
//                fileInfo.setCompletedSize(completedSize);
            }
            accessFile.seek(completedSize);
            inputStream = responseBody.byteStream();
            byte[] buffer = new byte[2048];
            bis = new BufferedInputStream(inputStream);

//            while ((length = bis.read(buffer)) > 0 &&
//                    (DownloadStatus.DOWNLOADING.equals(fileInfo.getDownloadStatus()))) {
//                accessFile.write(buffer, 0, length);
//                completedSize += length;
//            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }


}
