package com.yyxnb.what.popup.code;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.yyxnb.what.popup.R;
import com.yyxnb.what.popup.animator.PopupAnimator;
import com.yyxnb.what.popup.animator.ScaleAlphaAnimator;
import com.yyxnb.what.popup.util.PopupUtils;

import static com.yyxnb.what.popup.animator.PopupAnimation.ScaleAlphaFromCenter;


/**
 * Description: 在中间显示的Popup
 */
public class CenterPopupView extends BasePopupView {
    protected FrameLayout centerPopupContainer;
    protected int bindLayoutId;
    protected int bindItemLayoutId;
    protected View contentView;
    public CenterPopupView(@NonNull Context context) {
        super(context);
        centerPopupContainer = findViewById(R.id.centerPopupContainer);
    }

    protected void addInnerContent(){
        contentView = LayoutInflater.from(getContext()).inflate(initLayoutResId(), centerPopupContainer, false);
        LayoutParams params = (LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.CENTER;
        centerPopupContainer.addView(contentView, params);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout._popup_center_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        if(centerPopupContainer.getChildCount()==0)addInnerContent();
        getPopupContentView().setTranslationX(popupInfo.offsetX);
        getPopupContentView().setTranslationY(popupInfo.offsetY);
        PopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTranslationY(0);
    }

    /**
     * 具体实现的类的布局
     *
     * @return
     */
    @Override
    protected int initLayoutResId() {
        return 0;
    }

    @Override
    protected int getMaxWidth() {
        return popupInfo.maxWidth==0 ? (int) (PopupUtils.getWindowWidth(getContext()) * 0.8f)
                : popupInfo.maxWidth;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), ScaleAlphaFromCenter);
    }
}
