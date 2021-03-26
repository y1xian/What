package com.yyxnb.what.floatwindow;

/**
 * @author yyx
 */
public interface ViewStateListener {

    void onPositionUpdate(int x, int y);

    void onShow();

    void onHide();

    void onDismiss();

    void onMoveAnimStart();

    void onMoveAnimEnd();

    void onBackToDesktop();
}
