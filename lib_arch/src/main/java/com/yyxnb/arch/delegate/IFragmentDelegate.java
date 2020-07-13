package com.yyxnb.arch.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public interface IFragmentDelegate {

    void onAttached(Context context);

    void onCreated(Bundle savedInstanceState);

    void onViewCreated(View view, Bundle savedInstanceState);

    void onActivityCreated(Bundle savedInstanceState);

    void onStarted();

    void onResumed();

    void onPaused();

    void onStopped();

    void onSaveInstanceState(Bundle outState);

    void onViewDestroyed();

    void onDestroyed();

    void onDetached();

    boolean isAdd();

}
