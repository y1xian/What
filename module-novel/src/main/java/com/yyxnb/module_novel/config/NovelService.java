package com.yyxnb.module_novel.config;

import androidx.lifecycle.LiveData;

import com.yyxnb.common_res.bean.JiSuData;
import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.bean.BookDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.yyxnb.common_res.config.BaseAPI.HEADER_JISU;

public interface NovelService {

    @Headers(HEADER_JISU)
    @GET("{cst}/chapter")
    LiveData<JiSuData<List<BookChapterBean>>> getChapterList(
            @Path("cst") String cst,
            @Query("appkey") String appkey
    );

    @Headers(HEADER_JISU)
    @GET("{cst}/detail")
    LiveData<JiSuData<BookDetailBean>> getChapterDetail(
            @Path("cst") String cst,
            @Query("appkey") String appkey,
            @Query("detailid") String detailid,
            @Query("isdetailed") String isdetailed
    );
}
