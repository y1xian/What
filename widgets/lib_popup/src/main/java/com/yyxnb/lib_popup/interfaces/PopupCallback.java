package com.yyxnb.lib_popup.interfaces;


import com.yyxnb.lib_popup.code.BasePopupView;

/**
 * Description: XPopup显示和隐藏的回调接口，如果你不想重写3个方法，则可以使用SimpleCallback，
 * 它是一个默认实现类
 */
public interface PopupCallback {
    /**
     * 弹窗的onCreate方法执行完调用
     */
    void onCreated(BasePopupView popupView);

    /**
     * 在show之前执行，由于onCreated只执行一次，如果想多次更新数据可以在该方法中
     */
    void beforeShow(BasePopupView popupView);

    /**
     * 完全显示的时候执行
     */
    void onShow(BasePopupView popupView);

    /**
     * 完全消失的时候执行
     */
    void onDismiss(BasePopupView popupView);

    /**
     * 准备消失的时候执行
     */
    void beforeDismiss(BasePopupView popupView);

    /**
     * 暴漏返回按键的处理，如果返回true，XPopup不会处理；如果返回false，XPopup会处理，
     *
     * @return
     */
    boolean onBackPressed(BasePopupView popupView);
}
