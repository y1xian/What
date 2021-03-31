package com.yyxnb.what.popup.impl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.yyxnb.what.popup.PopupManager;
import com.yyxnb.what.popup.R;
import com.yyxnb.what.popup.interfaces.OnCancelListener;
import com.yyxnb.what.popup.interfaces.OnInputConfirmListener;
import com.yyxnb.what.popup.util.PopupUtils;


/**
 * Description: 带输入框，确定和取消的对话框
 */
public class InputConfirmPopupView extends ConfirmPopupView implements View.OnClickListener{

    /**
     *
     * @param context
     * @param bindLayoutId  在Confirm弹窗基础上需要增加一个id为et_input的EditText
     */
    public InputConfirmPopupView(@NonNull Context context, int bindLayoutId) {
        super(context, bindLayoutId);
    }

    EditText et_input;
    public CharSequence inputContent;
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        et_input = findViewById(R.id.etInput);
        et_input.setVisibility(VISIBLE);
        if(!TextUtils.isEmpty(hint)){
            et_input.setHint(hint);
        }
        if(!TextUtils.isEmpty(inputContent)){
            et_input.setText(inputContent);
            et_input.setSelection(inputContent.length());
        }
        applyPrimary();
    }

    public EditText getEditText() {
        return et_input;
    }

    protected void applyPrimary(){
        super.applyPrimaryColor();
        if(bindItemLayoutId==0){
            PopupUtils.setCursorDrawableColor(et_input, PopupManager.getPrimaryColor());
            et_input.post(new Runnable() {
                @Override
                public void run() {
                    BitmapDrawable defaultDrawable = PopupUtils.createBitmapDrawable(getResources(), et_input.getMeasuredWidth(), Color.parseColor("#888888"));
                    BitmapDrawable focusDrawable = PopupUtils.createBitmapDrawable(getResources(), et_input.getMeasuredWidth(), PopupManager.getPrimaryColor());
                    et_input.setBackgroundDrawable(PopupUtils.createSelector(defaultDrawable, focusDrawable));
                }
            });
        }
    }

    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;
    public void setListener( OnInputConfirmListener inputConfirmListener,OnCancelListener cancelListener){
        this.cancelListener = cancelListener;
        this.inputConfirmListener = inputConfirmListener;
    }

    @Override
    public void onClick(View v) {
        if(v==tv_cancel){
            if(cancelListener!=null)cancelListener.onCancel();
            dismiss();
        }else if(v==tv_confirm){
            if(inputConfirmListener!=null)inputConfirmListener.onConfirm(et_input.getText().toString().trim());
            if(popupInfo.autoDismiss)dismiss();
        }
    }
}
