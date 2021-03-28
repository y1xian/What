package com.yyxnb.common_res.utils;

import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * url 拦截器, 动态替换url
 */
public class UrlInterceptor implements Interceptor {

    public UrlInterceptor(Map<String, HttpUrl> urlBucket) {
        this.urlBucket = urlBucket;
    }

    public UrlInterceptor(List<String> url) {
        Map<String, HttpUrl> map = new HashMap<>();
        for (String s : url) {
            map.put(s, Objects.requireNonNull(HttpUrl.parse(s)));
        }
        this.urlBucket = map;
    }

    public static final String URL_PREFIX = "URL:";
    private Map<String, HttpUrl> urlBucket;

    private LruCache<String, String> urlCache = new LruCache<>(100);
    private static final String URL_KEY = "URL";

    private int pathSize = 0;

    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(processRequest(chain.request()));
    }

    private Request processRequest(Request request) {
        return request.newBuilder()
                .url(getHttpUrl(request))
                .build();
    }

    private HttpUrl getHttpUrl(Request request) {
        String urlKey = getUrlFromHeader(request);

        HttpUrl oldHttpUrl = request.url();

        if (urlBucket.get(urlKey) != null) {
            return parseHeaderHttpUrl(urlBucket.get(urlKey), oldHttpUrl);
        } else {
            return oldHttpUrl;
        }
    }

    /**
     * 判断是否有 @Header 注解, 并且有 URL: www.xxx.com 标示
     */
    private String getUrlFromHeader(Request request) {
        List<String> headers = request.headers(URL_KEY);

        if (headers.size() == 0) {
            return null;
        }

        if (headers.size() > 1) {
            throw new IllegalArgumentException("Only one URL_PREFIX in the headers");
        }

        return request.header(URL_KEY);
    }


    private HttpUrl parseHeaderHttpUrl(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {

        if (headerHttpUrl != null) {
            pathSize = headerHttpUrl.pathSize();
            if (headerHttpUrl.pathSegments().get(headerHttpUrl.pathSegments().size() - 1) != null) {
                pathSize -= 1;
            }

            HttpUrl newHttpUrl = createHttpUrl(headerHttpUrl, oldHttpUrl);
            Log.w("-----", "pageSize = " + pathSize + ", old http url is " + oldHttpUrl + ", new http url is " + newHttpUrl);
            return newHttpUrl;
        }

        return null;
    }

    /**
     * 重建新的HttpUrl，修改需要修改的url部分
     */
    private HttpUrl createHttpUrl(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {
        HttpUrl.Builder builder = createHttpBuilder(headerHttpUrl, oldHttpUrl);

        final HttpUrl newHttpUrl = builder
                .scheme(headerHttpUrl.scheme())
                .host(headerHttpUrl.host())
                .port(headerHttpUrl.port())
                .build();

        updateUrlCache(headerHttpUrl, oldHttpUrl, newHttpUrl);

        return newHttpUrl;
    }

    private HttpUrl.Builder createHttpBuilder(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {

        String urlFromCache = getUrlFromCache(headerHttpUrl, oldHttpUrl);

        if (urlFromCache != null) {
            HttpUrl.Builder builder = oldHttpUrl.newBuilder();
            return builder.encodedPath(urlFromCache);
        } else {
            return realCreateHttpBuilder(headerHttpUrl, oldHttpUrl);
        }
    }

    @SuppressWarnings("UseBulkOperation")
    private HttpUrl.Builder realCreateHttpBuilder(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {
        HttpUrl.Builder builder = oldHttpUrl.newBuilder();

        try {
            for (int i = 0; i < oldHttpUrl.pathSize(); i++) {
                builder.removePathSegment(0);
            }

            List<String> newPathSegments = new ArrayList<>();
            newPathSegments.addAll(headerHttpUrl.encodedPathSegments());

            if (oldHttpUrl.pathSize() < pathSize) {
                throw new IllegalArgumentException(headerHttpUrl + " pathSize more than " + oldHttpUrl + " pathSize");
            }

            List<String> encodedPathSegments = oldHttpUrl.encodedPathSegments();

            for (int i = 0; i < encodedPathSegments.size(); i++) {
                newPathSegments.add(encodedPathSegments.get(i));
            }

            for (String pathSegment : newPathSegments) {
                builder.addEncodedPathSegment(pathSegment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder;
    }

    //缓存
    private void updateUrlCache(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl, HttpUrl newHttpUrl) {
        String key = getKey(headerHttpUrl, oldHttpUrl);
        if (urlCache.get(key) == null) {
            urlCache.put(key, newHttpUrl.encodedPath());
        }
    }

    private String getUrlFromCache(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {
        String key = getKey(headerHttpUrl, oldHttpUrl);
        return urlCache.get(key);
    }

    private String getKey(HttpUrl headerHttpUrl, HttpUrl oldHttpUrl) {
        return pathSize + " - " + (headerHttpUrl.encodedPath()) + " - " + (oldHttpUrl.encodedPath());
    }

}
