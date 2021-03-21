package com.yyxnb.module_widget.ui.function.download;

import android.os.Bundle;
import android.util.Log;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.what.download.download.DownloadListner;
import com.yyxnb.what.download.download.DownloadManager;
import com.yyxnb.module_widget.R;

import java.util.List;

import cn.hutool.core.collection.ListUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/26
 * 描    述：文件下载
 * ================================================
 */
public class WidgetDownloadFragment extends BaseFragment {

    private DownloadManager manager;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_widget_download;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        findViewById(R.id.btn_start).setOnClickListener(v -> {
            for (String file : getData()) {
                manager.add(file, new DownloadListner() {
                    @Override
                    public void onFinished(String path) {
                        Log.e("onFinished", "onFinished " + path);
                    }

                    @Override
                    public void onProgress(float progress) {
//                        Log.w("onProgress", file + " ," + progress);
                    }

                    @Override
                    public void onPause() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
//                manager.download(file);
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(v -> {

        });
    }

    @Override
    public void initViewData() {
        manager = DownloadManager.getInstance();


    }

    private List<String> getData() {

        return ListUtil.list(false,
//                "https://cdn.410wl.cn/api/1608804931-852.apk",
//                "https://cdn.410wl.cn/api/1608803679-693.apk",
//                "https://cdn.410wl.cn/api/1608803227-888.apk",
//                "https://cdn.410wl.cn/api/1608801455-887.apk",
//                "https://cdn.410wl.cn/api/1608757603-182.apk",
//                "https://cdn.410wl.cn/api/1608754859-850.apk"
//                "https://cdn.410wl.cn/api/1608754529-211.apk",
//                "https://cdn.410wl.cn/api/1608753526-182.apk",
//                "https://cdn.410wl.cn/api/1608563089-545.apk",
//                "https://cdn.410wl.cn/api/1608563121-628.apk",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/1.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/2.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/3.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/4.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/5.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/6.mp4",
//                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/7.mp4",
                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/8.mp4",
                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/9.mp4",
                "https://video-bbcc.oss-cn-shenzhen.aliyuncs.com/short-video/10.mp4"
        );
    }
}