package com.yyxnb.what.room.cache;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yyxnb.what.app.AppUtils;
import com.yyxnb.what.room.DateConverter;

/**
 * Database这个对象我们需要指定三个参数entities 代表数据库需要操作的实体类集合，第二个参数代表数据库的版本第三个参数代表在编译时，
 * 将数据库的模式信息导出到JSON文件中，这样可有利于我们更好的调试和排错，这里我们设置为了false即不导出到json文件中，一般数据库本身问题看LOG很容易发现。
 * <p>
 * TypeConverters 自定义类型(通常情况下，数据库存储的是基本类型float，int，String等)
 */
@Database(entities = {Cache.class}, version = 1, exportSchema = false)
//数据读取、存储时数据转换器,比如将写入时将Date转换成Long存储，读取时把Long转换Date返回
@TypeConverters(DateConverter.class)
public abstract class CacheDatabase extends RoomDatabase {

    private static CacheDatabase INSTANCE;

    public abstract CacheDao cacheDao();

    public static CacheDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (CacheDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            //创建一个内存数据库
                            //但是这种数据库的数据只存在于内存中，也就是进程被杀之后，数据随之丢失
                            //Room.inMemoryDatabaseBuilder()
                            Room.databaseBuilder(AppUtils.getApp(), CacheDatabase.class, "net_cache.db")
                                    //是否允许在主线程进行查询
                                    .allowMainThreadQueries()
                                    //数据库创建和打开后的回调
                                    //.addCallback()
                                    //设置查询的线程池
                                    //.setQueryExecutor()
                                    //.openHelperFactory()
                                    //room的日志模式
                                    //.setJournalMode()
                                    //数据库升级异常之后的回滚
                                    .fallbackToDestructiveMigration()
                                    //数据库升级异常后根据指定版本进行回滚
//                                    .fallbackToDestructiveMigrationFrom()
                                    // .addMigrations(CacheDatabase.sMigration)
                                    .build();
                }
            }
        }
        return INSTANCE;
    }


//    static Migration sMigration = new Migration(1, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("alter table teacher rename to student");
//            database.execSQL("alter table teacher add column teacher_age INTEGER NOT NULL default 0");
//        }
//    };
}
