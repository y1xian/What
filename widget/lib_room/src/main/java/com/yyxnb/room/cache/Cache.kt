package com.yyxnb.room.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cache")
data class Cache(
        @PrimaryKey(autoGenerate = false)
        var key: String = "",

        var data: ByteArray = byteArrayOf()

) : Serializable {

}