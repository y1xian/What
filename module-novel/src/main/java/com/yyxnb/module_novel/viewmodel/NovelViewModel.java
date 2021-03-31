package com.yyxnb.module_novel.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_res.bean.JiSuData;
import com.yyxnb.common_res.config.BaseConfig;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.bean.BookDetailBean;
import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.module_novel.config.NovelService;
import com.yyxnb.module_novel.db.BookInfoDao;
import com.yyxnb.module_novel.db.NovelDatabase;

import java.util.List;

public class NovelViewModel extends CommonViewModel {

    private NovelService mApi = Http.getInstance().create(NovelService.class);
    private BookInfoDao homeDao = NovelDatabase.getInstance().bookHomeDao();

    public MutableLiveData<List<BookChapterBean>> chapterList = new MutableLiveData<>();
    public MutableLiveData<BookDetailBean> chapterDetail = new MutableLiveData<>();

    public LiveData<BookInfoBean> reqBookData(int bookId) {
        return homeDao.getBookLive(bookId);
    }

    public void reqChapterList(String cst) {
        launchOnlyResult(mApi.getChapterList(cst, BaseConfig.JISU_APPKEY), new HttpResponseCallback<JiSuData<List<BookChapterBean>>>() {
            @Override
            public void onSuccess(JiSuData<List<BookChapterBean>> data) {
                chapterList.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    public void reqChapterDetail(String cst, String detailid) {
        launchOnlyResult(mApi.getChapterDetail(cst, BaseConfig.JISU_APPKEY, detailid, "1"),
                new HttpResponseCallback<JiSuData<BookDetailBean>>() {
            @Override
            public void onSuccess(JiSuData<BookDetailBean> data) {
                chapterDetail.postValue(data.getResult());
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

}
