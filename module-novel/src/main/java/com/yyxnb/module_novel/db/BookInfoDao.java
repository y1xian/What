package com.yyxnb.module_novel.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.lib_room.BaseDao;

@Dao
public interface BookInfoDao extends BaseDao<BookInfoBean> {

    @Query("SELECT * FROM bookInfo WHERE bookId = :bookId LIMIT 1")
    LiveData<BookInfoBean> getBookLive(int bookId);

    @Query("SELECT * FROM bookInfo WHERE bookId = :bookId LIMIT 1")
    BookInfoBean getBook(int bookId);

}
