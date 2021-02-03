package com.yyxnb.common_res.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

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
/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/10
 * 历    史：
 * 描    述：用户基本信息
 * ================================================
 */
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
    // 登录状态 0未登录 -1游客 1手机号登录 2微信登录 3qq登录 4微博登录 ，10黑名单用户
    public int loginStatus;


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
                ", loginStatus=" + loginStatus +
                '}';
    }
}
