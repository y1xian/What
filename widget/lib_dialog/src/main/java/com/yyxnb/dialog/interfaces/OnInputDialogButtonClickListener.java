package com.yyxnb.dialog.interfaces;

import android.view.View;

import com.yyxnb.dialog.core.BaseDialogFragment;


/**
 */
public interface OnInputDialogButtonClickListener {
    
    boolean onClick(BaseDialogFragment baseDialogFragment, View v, String inputStr);
}
