package com.yyxnb.what.popup.code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.yyxnb.what.popup.R;
import com.yyxnb.what.popup.animator.PopupAnimation;
import com.yyxnb.what.popup.animator.PopupAnimator;
import com.yyxnb.what.popup.animator.TranslateAnimator;
import com.yyxnb.what.popup.enums.PopupStatus;
import com.yyxnb.what.popup.util.KeyboardUtils;
import com.yyxnb.what.popup.util.PopupUtils;
import com.yyxnb.what.popup.widget.SmartDragLayout;

/**
 * Description: 在底部显示的Popup
 */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;

    public BottomPopupView(@NonNull Context context) {
        super(context);
        bottomPopupContainer = findViewById(R.id.bottomPopupContainer);
    }

    protected void addInnerContent() {
        View contentView = LayoutInflater.from(getContext()).inflate(initLayoutResId(), bottomPopupContainer, false);
        bottomPopupContainer.addView(contentView);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout._popup_bottom_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        if (bottomPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        bottomPopupContainer.enableDrag(popupInfo.enableDrag);
        bottomPopupContainer.dismissOnTouchOutside(popupInfo.isDismissOnTouchOutside);
        bottomPopupContainer.hasShadowBg(popupInfo.hasShadowBg);
        bottomPopupContainer.isThreeDrag(popupInfo.isThreeDrag);

        getPopupImplView().setTranslationX(popupInfo.offsetX);
        getPopupImplView().setTranslationY(popupInfo.offsetY);

        PopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());

        bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() {
            @Override
            public void onClose() {
                doAfterDismiss();
            }

            @Override
            public void onOpen() {
                BottomPopupView.super.doAfterShow();
            }
        });

        bottomPopupContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void doAfterShow() {
        if (popupInfo.enableDrag) {
            //do nothing self.
        } else {
            super.doAfterShow();
        }
    }

    @Override
    public void doShowAnimation() {
        if (popupInfo.enableDrag) {
            bottomPopupContainer.open();
        } else {
            super.doShowAnimation();
        }
    }

    @Override
    public void doDismissAnimation() {
        if (popupInfo.enableDrag) {
            bottomPopupContainer.close();
        } else {
            super.doDismissAnimation();
        }
    }

    /**
     * 动画是跟随手势发生的，所以不需要额外的动画器，因此动画时间也清零
     *
     * @return
     */
    @Override
    public int getAnimationDuration() {
        return popupInfo.enableDrag ? 0 : super.getAnimationDuration();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        // 移除默认的动画器
        return popupInfo.enableDrag ? null : new TranslateAnimator(getPopupContentView(), PopupAnimation.TranslateFromBottom);
    }

    @Override
    public void dismiss() {
        if (popupInfo.enableDrag) {
            if (popupStatus == PopupStatus.Dismissing) {
                return;
            }
            popupStatus = PopupStatus.Dismissing;
            if (popupInfo.autoOpenSoftInput) {
                KeyboardUtils.hideSoftInput(this);
            }
            clearFocus();
            // 关闭Drawer，由于Drawer注册了关闭监听，会自动调用dismiss
            bottomPopupContainer.close();
        } else {
            super.dismiss();
        }
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
        return popupInfo.maxWidth == 0 ? PopupUtils.getWindowWidth(getContext())
                : popupInfo.maxWidth;
    }

    @Override
    protected View getTargetSizeView() {
        return getPopupImplView();
    }

}
