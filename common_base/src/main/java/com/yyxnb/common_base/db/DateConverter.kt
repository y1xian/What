package com.yyxnb.common_base.db

import androidx.room.TypeConverter
import java.util.*

/**
 * 在这个类中，我们使用了@TypeConverter，converterDate将Date转换成数据库可以保存的类型，revertDate将数据库保存的值转换成Date
 */
object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}