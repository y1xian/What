package com.yyxnb.popup.interfaces;


import com.yyxnb.popup.code.BasePopupView;

/**
 * Description:
 */
public class SimpleCallback implements PopupCallback {
    @Override
    public void onCreated(BasePopupView popupView) {

    }
    @Override
    public void beforeShow(BasePopupView popupView) {

    }

    @Override
    public void onShow(BasePopupView popupView) {

    }
    @Override
    public void onDismiss(BasePopupView popupView) {

    }

    @Override
    public void beforeDismiss(BasePopupView popupView) {

    }

    @Override
    public boolean onBackPressed(BasePopupView popupView) {
        return false;
    }
}
