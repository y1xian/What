package com.yyxnb.what.arch.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.yyxnb.what.core.interfaces.ILifecycle;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/21
 * 历    史：
 * 描    述：IActivityDelegate
 * ================================================
 */
public interface IActivityDelegate extends ILifecycle {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Activity activity, Bundle outState);

    void onDestroy();

}
