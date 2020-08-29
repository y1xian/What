package com.yyxnb.dialog.interfaces;


import com.yyxnb.dialog.core.BaseDialog;

public interface DialogLifeCycleListener {

    void onCreate(BaseDialog dialog);

    void onShow(BaseDialog dialog);

    void onDismiss(BaseDialog dialog);

}
