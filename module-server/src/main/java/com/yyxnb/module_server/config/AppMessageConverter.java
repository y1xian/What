package com.yyxnb.module_server.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yanzhenjie.andserver.annotation.Converter;
import com.yanzhenjie.andserver.framework.MessageConverter;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.ResponseBody;
import com.yanzhenjie.andserver.util.IOUtils;
import com.yanzhenjie.andserver.util.MediaType;
import com.yyxnb.what.okhttp.utils.GsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/13
 * 描    述：转换器，结果输出为json https://yanzhenjie.com/AndServer/annotation/Converter.html
 * ================================================
 */
@Converter
public class AppMessageConverter implements MessageConverter {

    @Override
    public ResponseBody convert(@Nullable Object output, @Nullable MediaType mediaType) {
        return new JsonBody(GsonUtils.getGson().toJson(output));
    }

    @Nullable
    @Override
    public <T> T convert(@NonNull InputStream stream, @Nullable MediaType mediaType, Type type) throws IOException {
        Charset charset = mediaType == null ? null : mediaType.getCharset();
        if (charset == null) {
            return GsonUtils.getGson().fromJson(IOUtils.toString(stream), type);
        }
        return GsonUtils.getGson().fromJson(IOUtils.toString(stream, charset), type);
    }
}