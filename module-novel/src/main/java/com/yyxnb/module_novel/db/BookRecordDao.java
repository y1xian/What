package com.yyxnb.module_novel.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.yyxnb.module_novel.bean.BookRecordBean;
import com.yyxnb.what.room.BaseDao;

import java.util.List;

@Dao
public interface BookRecordDao extends BaseDao<BookRecordBean> {

    @Query("SELECT * FROM bookRecord")
    List<BookRecordBean> getAll();

    @Query("SELECT * FROM bookRecord WHERE userId IN (:userId)")
    List<BookRecordBean> getUserAll(String userId);

    @Query("SELECT * FROM bookRecord WHERE bookId IN (:ids)")
    List<BookRecordBean> getAllByIds(long[] ids);

    @Query("SELECT * FROM bookRecord WHERE bookId IN (:ids)")
    BookRecordBean getAllByIds(String ids);

    @Query("SELECT * FROM bookRecord WHERE userId IN (:userId) AND bookId IN (:bookId)")
    BookRecordBean getUserId(String userId, String bookId);

    @Query("DELETE FROM bookRecord WHERE bookId IN (:bookId)")
    void deleteById(String bookId);

    @Query("DELETE FROM bookRecord WHERE userId IN (:userId) AND bookId IN (:bookId)")
    void deleteUserId(String userId, String bookId);

    @Query("DELETE FROM bookRecord")
    void deleteAll();


}
