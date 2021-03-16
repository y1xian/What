package com.yyxnb.module_novel.bean;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

/**
 * 书架
 */
@Entity(tableName = "bookShelf", indices = {@Index(value = {"userId", "bookId"}, unique = true)})
public class BookShelfBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userId;

    /**
     * 需要更新
     */
    public boolean hasUpdate;

    /**
     * 加入时间
     */
    public long addTime;

    @NonNull
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    public BookInfoBean bookInfoBean;
}