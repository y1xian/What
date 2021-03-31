package com.yyxnb.module_novel.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.what.room.BaseDao;

@Dao
public interface BookInfoDao extends BaseDao<BookInfoBean> {

    @Query("SELECT * FROM bookInfo WHERE bookId = :bookId LIMIT 1")
    LiveData<BookInfoBean> getBookLive(int bookId);

    @Query("SELECT * FROM bookInfo WHERE bookId = :bookId LIMIT 1")
    BookInfoBean getBook(int bookId);

}
