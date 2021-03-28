package com.yyxnb.what.room;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * 在这个类中，我们使用了@TypeConverter，converterDate将Date转换成数据库可以保存的类型，revertDate将数据库保存的值转换成Date
 */
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}