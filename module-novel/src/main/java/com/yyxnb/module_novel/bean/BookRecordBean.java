package com.yyxnb.module_novel.bean;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

/**
 * 阅读记录
 */
@Entity(tableName = "bookRecord", indices = {@Index(value = {"userId", "bookId"}, unique = true)})
public class BookRecordBean {
    //所属的书的id
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userId;

    //阅读到了第几章
    public int chapter;
    //当前的页码
    public int pagePos;

    public long addTime;

    @NonNull
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    public BookInfoBean bookInfoBean;

    @Override
    public String toString() {
        return "BookRecordBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", chapter=" + chapter +
                ", pagePos=" + pagePos +
                ", addTime=" + addTime +
                ", bookInfoBean=" + bookInfoBean +
                '}';
    }
}
