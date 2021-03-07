package com.yyxnb.module_novel.view;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyxnb.module_novel.bean.BookChapterBean;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter {

    @TypeConverter
    public List<BookChapterBean> stringToObject(String value) {
        Type type = new TypeToken<List<BookChapterBean>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public String objectToString(List<BookChapterBean> chapterBeans) {
        return new Gson().toJson(chapterBeans);
    }
}