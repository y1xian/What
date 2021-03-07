package com.yyxnb.module_wanandroid.bean;

import java.util.List;

public class WanStatus<T> {
    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<T> datas;
}
