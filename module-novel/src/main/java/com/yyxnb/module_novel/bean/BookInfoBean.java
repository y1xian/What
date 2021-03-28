package com.yyxnb.module_novel.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * 书籍信息
 */
@Entity(tableName = "bookInfo")
public class BookInfoBean {

    @PrimaryKey
    public int bookId;

    public int type;

    public String title;

    public String des;

    public String picture;

    public String label;

    public String author;

    @Override
    public String toString() {
        return "BookInfoBean{" +
                "bookId=" + bookId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", picture='" + picture + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
