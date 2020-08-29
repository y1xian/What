package com.yyxnb.dialog.interfaces;

import android.view.View;

import com.yyxnb.dialog.core.BaseDialog;


/**
 */
public interface OnInputDialogButtonClickListener {
    
    boolean onClick(BaseDialog baseDialog, View v, String inputStr);
}
