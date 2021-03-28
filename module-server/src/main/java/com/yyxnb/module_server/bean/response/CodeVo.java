package com.yyxnb.module_server.bean.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "code")
public class CodeVo {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String phone;
    @NonNull
    private String code;

    public CodeVo() {
    }

    @Ignore
    public CodeVo(@NonNull String phone, @NonNull String code) {
        this.phone = phone;
        this.code = code;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }
}
