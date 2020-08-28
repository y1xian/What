package com.yyxnb.common_base.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import java.io.Serializable;

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

@Entity(tableName = "user",
        // 复合主键 ps: @PrimaryKey 和 primaryKeys 不能并存，多主键只能primaryKeys
        primaryKeys = {"userId", "phone"},
        // 约束 唯一
        indices = {@Index(value = {"userId", "phone", "token"}, unique = true)}
)
public class UserBean implements Serializable {

    // 自增
//    @PrimaryKey(autoGenerate = true)
//    public int id;

    public int userId = 666;
    @NonNull
    public String phone;
    public String token;
    public String nickname;
    public String avatar;
    public String signature;
    public boolean isLogin;
    public int sex;
    public int age;


    @Override
    public String toString() {
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
                '}';
    }
}
