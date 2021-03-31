package com.yyxnb.module_novel.bean;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

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