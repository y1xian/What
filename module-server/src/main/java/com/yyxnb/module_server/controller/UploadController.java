package com.yyxnb.module_server.controller;

import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yyxnb.module_server.bean.JsonResultVo;
import com.yyxnb.module_server.ServerManager;
import com.yyxnb.what.core.log.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.hutool.core.io.FileUtil;

@RestController
public class UploadController {

    /**
     * 文件上传
     *
     * @param file
     * @throws IOException
     */
    @PostMapping(path = "/upload")
    public JsonResultVo<String> upload(@RequestParam(name = "file") MultipartFile file) throws IOException {
        File localFile = FileUtil.file(new File(ServerManager.getInstance().uploadTempDir(), file.getFilename()));
        file.transferTo(localFile);
        LogUtils.w(String.format("文件存储路径 %s", localFile.getAbsolutePath()));
        return JsonResultVo.successData(localFile.getAbsolutePath());
    }

    /**
     * 文件列表
     *
     * @return
     */
    @RequestMapping(path = "/fileList", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResultVo<List<String>> fileList() {
        List<String> fileNames = FileUtil.listFileNames(ServerManager.getInstance().uploadTempDir().getAbsolutePath());
        LogUtils.list(fileNames);
        return JsonResultVo.successData(fileNames);
    }

    /**
     * 文件存储位置
     *
     * @return
     */
    @RequestMapping(path = "/filePath", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResultVo<String> filePath() {
        return JsonResultVo.successData(ServerManager.getInstance().uploadTempDir().getAbsolutePath());
    }


}