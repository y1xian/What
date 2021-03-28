package com.yyxnb.module_widget.bean;


import com.yyxnb.what.core.interfaces.IData;

import java.io.Serializable;
import java.util.Objects;

public class MainBean implements IData<Long>, Serializable {

    public int id;
    public int type;
    public String title;
    public String des;
    public String url;
    public String key;

    @Override
    public int id() {
        return id;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return des;
    }

    @Override
    public Long getResult() {
        return 0L;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainBean)) return false;
        MainBean mainBean = (MainBean) o;
        return id == mainBean.id &&
                type == mainBean.type &&
                Objects.equals(title, mainBean.title) &&
                Objects.equals(des, mainBean.des) &&
                Objects.equals(url, mainBean.url) &&
                Objects.equals(key, mainBean.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, title, des, url, key);
    }
}
