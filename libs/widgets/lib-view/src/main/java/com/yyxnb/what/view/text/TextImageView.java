package com.yyxnb.what.view.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yyxnb.what.view.R;
/*
            <TextImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text=""
                    android:drawableStart="@drawable/pic"
                    app:drawableStartHeight="20dp"
                    app:drawableStartWidth="20dp"/>
 */

/**
 * TextView 中drawable 图片大小
 *
 * @author yyx
 */
@SuppressLint("AppCompatCustomView")
public class TextImageView extends TextView {

    private int mLeftWidth;
    private int mLeftHeight;
    private int mTopWidth;
    private int mTopHeight;
    private int mRightWidth;
    private int mRightHeight;
    private int mBottomWidth;
    private int mBottomHeight;

    public TextImageView(Context context) {
        super(context);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextImageView);

        mLeftWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftWidth, 0);
        mLeftHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableLeftHeight, 0);
        mTopWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopWidth, 0);
        mTopHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableTopHeight, 0);
        mRightWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightWidth, 0);
        mRightHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableRightHeight, 0);
        mBottomWidth = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomWidth, 0);
        mBottomHeight = typedArray.getDimensionPixelOffset(R.styleable.TextImageView_drawableBottomHeight, 0);
        typedArray.recycle();
        setDrawablesSize();
    }

    private void setDrawablesSize() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        for (int i = 0; i < compoundDrawables.length; i++) {
            switch (i) {
                case 0:
                    setDrawableBounds(compoundDrawables[0], mLeftWidth, mLeftHeight);
                    break;
                case 1:
                    setDrawableBounds(compoundDrawables[1], mTopWidth, mTopHeight);
                    break;
                case 2:
                    setDrawableBounds(compoundDrawables[2], mRightWidth, mRightHeight);
                    break;
                case 3:
                    setDrawableBounds(compoundDrawables[3], mBottomWidth, mBottomHeight);
                    break;
                default:
                    break;
            }

        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

    private void setDrawableBounds(Drawable drawable, int width, int height) {
        if (drawable != null) {
            double scale = ((double) drawable.getIntrinsicHeight()) / ((double) drawable.getIntrinsicWidth());
            drawable.setBounds(0, 0, width, height);
            Rect bounds = drawable.getBounds();
            //高宽只给一个值时，自适应
            if (bounds.right != 0 || bounds.bottom != 0) {
                if (bounds.right == 0) {
                    bounds.right = (int) (bounds.bottom / scale);
                    drawable.setBounds(bounds);
                }
                if (bounds.bottom == 0) {
                    bounds.bottom = (int) (bounds.right * scale);
                    drawable.setBounds(bounds);
                }
            }

        }
    }
}