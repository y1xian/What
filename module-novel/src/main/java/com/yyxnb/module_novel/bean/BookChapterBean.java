package com.yyxnb.module_novel.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.yyxnb.module_novel.view.ListConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 书的章节链接(作为下载的进度数据)
 * 同时作为网络章节和本地章节 (没有找到更好分离两者的办法)
 */
@Entity(tableName = "bookChapter", indices = {@Index(value = {"chapterid", "detailid", "bookId"}, unique = false)})
@TypeConverters(ListConverter.class)
public class BookChapterBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String bookId;

    public String link;

    public String chapterid;

//    public int indexId;

    public String chaptername;

    public int detailid;

    public String name;

    public int parentid;

    @Ignore
    public boolean selected;
//
//    @Ignore
//    public int status;
//
//    //在书籍文件中的起始位置
//    public long start;
//
//    //在书籍文件中的终止位置
//    public long end;

    public List<BookChapterBean> list = new ArrayList<>();
}