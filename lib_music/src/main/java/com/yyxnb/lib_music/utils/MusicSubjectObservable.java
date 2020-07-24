package com.yyxnb.lib_music.utils;

import java.util.Observable;

/**
 * Observable
 */
public class MusicSubjectObservable extends Observable {

    public MusicSubjectObservable() {
    }

    public void updataSubjectObserivce(Object data) {
        setChanged();
        notifyObservers(data);
    }
}
