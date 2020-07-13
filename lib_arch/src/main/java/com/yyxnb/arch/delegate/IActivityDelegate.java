package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.os.Bundle;

public interface IActivityDelegate {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Activity activity, Bundle outState);

    void onDestroy();

}
