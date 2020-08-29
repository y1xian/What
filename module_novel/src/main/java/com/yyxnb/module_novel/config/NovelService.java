package com.yyxnb.module_novel.config;

import com.yyxnb.common_base.bean.JiSuData;
import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.bean.BookDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.yyxnb.common_base.config.BaseAPI.HEADER_JISU;

public interface NovelService {

    @Headers(HEADER_JISU)
    @GET("{cst}/chapter")
    Observable<JiSuData<List<BookChapterBean>>> getChapterList(
            @Path("cst") String cst,
            @Query("appkey") String appkey
    );

    @Headers(HEADER_JISU)
    @GET("{cst}/detail")
    Observable<JiSuData<BookDetailBean>> getChapterDetail(
            @Path("cst") String cst,
            @Query("appkey") String appkey,
            @Query("detailid") String detailid,
            @Query("isdetailed") String isdetailed
    );
}
