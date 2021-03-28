package com.yyxnb.what.localservice.bean;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class LocalFolder implements Serializable {

    public String name;

    public int count;

    ArrayList<LocalMedia> localMedia = new ArrayList<>();

    public void addMedias(LocalMedia localMedia) {
        this.localMedia.add(localMedia);
    }

    public LocalFolder(String name) {
        this.name = name;
    }

    public ArrayList<LocalMedia> getLocalMedia() {
        return this.localMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setLocalMedia(ArrayList<LocalMedia> localMedia) {
        this.localMedia = localMedia;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalFolder)) {
            return false;
        }
        LocalFolder that = (LocalFolder) o;
        return getCount() == that.getCount() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getLocalMedia(), that.getLocalMedia());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(getName(), getCount(), getLocalMedia());
    }
}
