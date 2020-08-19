package com.yyxnb.common_base.bean

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import java.io.Serializable

/*

public @interface Entity {
    //定义表名
    String tableName() default "";
    //定义索引
    Index[] indices() default {};
    //设为true则父类的索引会自动被当前类继承
    boolean inheritSuperIndices() default false;
    //定义主键
    String[] primaryKeys() default {};
    //定义外键
    ForeignKey[] foreignKeys() default {};

 */
@Entity(tableName = "user", primaryKeys = ["userId", "phone"], indices = [Index(value = ["userId", "phone", "token"], unique = true)])
data class UserBean(
        @JvmField
        var userId: Int = 0,
        @JvmField
        @NonNull
        var phone: String = "",
        @JvmField
        var token: String? = null,
        @JvmField
        var nickname: String? = null,
        @JvmField
        var avatar: String? = null,
        @JvmField
        var signature: String? = null,
        @JvmField
        var isLogin: Boolean = false,
        var sex: Int = 0,
        var age: Int = 0
) : Serializable {
    override fun toString(): String {
        return "UserBean{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", signature='" + signature + '\'' +
                ", isLogin=" + isLogin +
                ", sex=" + sex +
                ", age=" + age +
                '}'
    }
}