package com.yyxnb.dialog.interfaces;


import com.yyxnb.dialog.core.BaseDialogFragment;

public interface DialogLifeCycleListener {

    void onCreate(BaseDialogFragment dialog);

    void onShow(BaseDialogFragment dialog);

    void onDismiss(BaseDialogFragment dialog);

}
