package com.yyxnb.lib_file;

import android.os.Environment;

import com.yyxnb.util_okhttp.AbsOkHttp;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/01/13
 * 描    述：下载、上传
 * ================================================
 */
public class DownUpLoadUtils extends AbsOkHttp {

    @Override
    protected String baseUrl() {
        return "";
    }

    /**
     * 文件下载保存目录
     */
    protected String downloadFileDir(){
        return Environment.getExternalStorageDirectory().getPath()+"/okHttp_download/";
    }



}
