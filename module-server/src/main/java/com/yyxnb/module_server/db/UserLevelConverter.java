package com.yyxnb.module_server.db;

import androidx.room.TypeConverter;

import com.yyxnb.module_server.constants.UserLevel;

public class UserLevelConverter {
    @TypeConverter
    public static UserLevel form(String value) {
        if (value.equals(UserLevel.VISITOR.getLevel())) {
            return UserLevel.VISITOR;
        } else if (value.equals(UserLevel.GENERAL.getLevel())) {
            return UserLevel.GENERAL;
        } else if (value.equals(UserLevel.VIP.getLevel())) {
            return UserLevel.VIP;
        } else if (value.equals(UserLevel.SVIP.getLevel())) {
            return UserLevel.SVIP;
        } else if (value.equals(UserLevel.BLACKLIST.getLevel())) {
            return UserLevel.BLACKLIST;
        } else if (value.equals(UserLevel.ADMIN.getLevel())) {
            return UserLevel.ADMIN;
        }
        return null;
    }

    @TypeConverter
    public static String to(UserLevel value) {
        return value.getLevel();
    }
}