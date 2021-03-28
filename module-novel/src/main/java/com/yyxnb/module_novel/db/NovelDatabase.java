package com.yyxnb.module_novel.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.module_novel.bean.BookRecordBean;
import com.yyxnb.module_novel.bean.BookShelfBean;
import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.room.DateConverter;

/**
 * Database这个对象我们需要指定三个参数entities 代表数据库需要操作的实体类集合，第二个参数代表数据库的版本第三个参数代表在编译时，
 * 将数据库的模式信息导出到JSON文件中，这样可有利于我们更好的调试和排错，这里我们设置为了false即不导出到json文件中，一般数据库本身问题看LOG很容易发现。
 * <p>
 * TypeConverters 自定义类型(通常情况下，数据库存储的是基本类型float，int，String等)
 */
@Database(entities = {BookChapterBean.class, BookInfoBean.class, BookRecordBean.class, BookShelfBean.class}
        , version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class NovelDatabase extends RoomDatabase {

    private static NovelDatabase INSTANCE;

    public abstract BookChapterDao bookChapterDao();

    public abstract BookInfoDao bookHomeDao();

    public abstract BookRecordDao bookRecordDao();

    public abstract BookShelfDao bookShelfDao();


    public static NovelDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (NovelDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(AppUtils.getApp(), NovelDatabase.class, "what_novel.db")
                                    .allowMainThreadQueries() //room默认数据库的查询是不能在主线程中执行的，除非这样设置
                                    .fallbackToDestructiveMigration() //不想提供migration，而且希望更新版本之后清空数据库
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}