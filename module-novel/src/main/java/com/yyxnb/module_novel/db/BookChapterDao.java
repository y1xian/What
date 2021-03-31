package com.yyxnb.module_novel.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface BookChapterDao extends BaseDao<BookChapterBean> {

    @Query("SELECT * FROM bookChapter")
    List<BookChapterBean> getAll();

    @Query("SELECT * FROM bookChapter WHERE bookId IN (:bookId)")
    List<BookChapterBean> getAllByIds(String bookId);

    @Query("SELECT * FROM bookChapter WHERE bookId IN (:bookId)")
    BookChapterBean getAllById(String bookId);

    @Query("DELETE FROM bookChapter WHERE bookId IN (:bookId)")
    void deleteById(String bookId);

    @Query("DELETE FROM bookChapter")
    void deleteAll();


}
