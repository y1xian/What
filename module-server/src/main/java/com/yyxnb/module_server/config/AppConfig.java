package com.yyxnb.module_server.config;

import android.content.Context;

import com.yanzhenjie.andserver.annotation.Config;
import com.yanzhenjie.andserver.framework.config.Multipart;
import com.yanzhenjie.andserver.framework.config.WebConfig;
import com.yanzhenjie.andserver.framework.website.AssetsWebsite;
import com.yyxnb.module_server.ServerManager;

@Config
public class AppConfig implements WebConfig {

    @Override
    public void onConfig(Context context, Delegate delegate) {
        delegate.addWebsite(new AssetsWebsite(context, "/web"));

        delegate.setMultipart(Multipart.newBuilder()
                // 允许上传的最大字节  100M
                .allFileMaxSize(1024 * 1024 * 100)
                // 单文件最大字节 1M
                .fileMaxSize(1024 * 1024)
                // 写入磁盘之前允许的最大大小字节 100KB
                .maxInMemorySize(1024 * 100)
                // 存储路径
                .uploadTempDir(ServerManager.getInstance().uploadTempDir())
                .build());
    }
}