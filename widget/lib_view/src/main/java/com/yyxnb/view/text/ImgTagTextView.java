package com.yyxnb.view.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 文字前面含有图片的TextView
 *
 * @author yyx
 */
public class ImgTagTextView extends View {

    //标签图片Bitmap
    private Bitmap flagBitMap;
    //bitmap的范围
    private Rect useBitmapRect;
    //标签和内容之间的距离
    private int flagAndContentSpace;
    //内容文字
    private String content;
    //内容文字颜色
    private int contentColor;
    //内容文字大小
    private int contentTextSize;
    //最大行数
    private int maxLine = 1;
    //最大长度
    private int maxLength;
    //末尾的文字
    private String endText = "...";
    //画笔
    private Paint paint;
    //bitmap的矩形框
    private Rect bitmapRect;
    private RectF bitmapRectF;
    //textPaint
    private TextPaint textPaint;

    public ImgTagTextView(Context context) {
        super(context);
        init();
    }

    public ImgTagTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImgTagTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImgTagTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化參數
     */
    private void init() {
        paint = new Paint();
        useBitmapRect = new Rect();
        bitmapRect = new Rect();
        bitmapRectF = new RectF();
        textPaint = new TextPaint();

    }

    /**
     * 设置bitmap的大小
     *
     * @param width
     * @param height
     */
    public void setBitmapSize(int width, int height) {
        useBitmapRect.left = 0;
        useBitmapRect.right = dp2px(width);
        useBitmapRect.top = 0;
        useBitmapRect.bottom = dp2px(height);
    }

    /**
     * @return get bitmap
     */
    public Bitmap getFlagBitMap() {
        return flagBitMap;
    }

    /**
     * set flag bitmap
     *
     * @param flagBitMap
     */
    public void setFlagBitMap(Bitmap flagBitMap) {
        this.flagBitMap = flagBitMap;
    }

    /**
     * @param dp distance
     * @return dp to px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    /**
     * @return width of screen
     */
    private int screenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @param flagAndContentSpace space of flag and content
     */
    public void setFlagAndContentSpace(int flagAndContentSpace) {
        this.flagAndContentSpace = dp2px(flagAndContentSpace);
    }

    /**
     * @return text of line end
     */
    public String getEndText() {
        return endText;
    }

    /**
     * @param endText line end text
     */
    public void setEndText(String endText) {
        this.endText = endText;
    }


    /**
     * @return content text of draw content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content text of draw content
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * @return color of draw content
     */
    public int getContentColor() {
        return contentColor;
    }

    /**
     * @param contentColor color for content
     */
    public void setContentColor(@ColorInt int contentColor) {
        this.contentColor = contentColor;
    }

    /**
     * @return size of content text unit px
     */
    public int getContentTextSize() {
        return contentTextSize;
    }

    /**
     * @param contentTextSize size of content text unit px
     */
    public void setContentTextSize(int contentTextSize) {
        this.contentTextSize = dp2px(contentTextSize);
    }

    /**
     * @return maxLine  of content text
     */
    public int getMaxLine() {
        return maxLine;
    }

    /**
     * @param maxLine maxLine  of content text
     */
    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    /**
     * @return maxLength   of content text
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength of content text
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (content == null || flagBitMap == null || width == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int viewHeight = 0;
        paint.setTextSize(contentTextSize);
        int oneLineHeight = (int) (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) + 1;
        float leftWidthSpace = width - (getPaddingLeft() + getPaddingRight()) - bitmapRectF.right - 1f - flagAndContentSpace;
        int length = content.length();
        int oneLineIndex = 0;
        for (int i = 0; i < length; i++) {
            if (paint.measureText(content.substring(0, i)) > leftWidthSpace) {
                oneLineIndex = i - 1;
                break;
            }
        }
        if (oneLineIndex == 0) {
            oneLineIndex = content.length();
        }
        if (maxLine != 1) {
            if (oneLineIndex < content.length()) {
                //说明有第二行
                String leftContent = content.substring(oneLineIndex);
                textPaint.set(paint);
                StaticLayout staticLayout;
                if (Build.VERSION.SDK_INT <= 23) {
                    staticLayout = new StaticLayout(leftContent,
                            0, leftContent.length(), textPaint,
                            getWidth() - getPaddingLeft() - getPaddingRight(),
                            Layout.Alignment.ALIGN_NORMAL,
                            0f,
                            0f,
                            false,
                            TextUtils.TruncateAt.END,
                            0);
                    //由于设置最大行数的方法是@hide的所以反射修改值
                    try {
                        Field mMaxLines = staticLayout.getClass().getDeclaredField("mMaxLines");
                        mMaxLines.setAccessible(true);
                        mMaxLines.set(staticLayout, maxLine - 1);
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                } else {
                    staticLayout = StaticLayout.Builder.obtain(leftContent, 0, leftContent.length(), textPaint, getWidth() - getPaddingLeft() - getPaddingRight())
                            .setEllipsize(TextUtils.TruncateAt.END)
                            .setMaxLines(maxLine - 1)
                            .build();
                }
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                //最大行数计算
                if (maxLine >= staticLayout.getLineCount() + 1) {
                    viewHeight += (fontMetrics.descent - fontMetrics.ascent) * staticLayout.getLineCount() + 1;
                } else {
                    viewHeight += (fontMetrics.descent - fontMetrics.ascent) * (maxLine - 1) + 1;
                }

            }
        }
        viewHeight += oneLineHeight + 1;
        Log.e("onMeasure", viewHeight + "");
        int heightSize = viewHeight == 0 ? MeasureSpec.getSize(heightMeasureSpec) : viewHeight;
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(resolveSize(heightSize, heightMeasureSpec), MeasureSpec.getMode(heightMeasureSpec)));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (content == null || flagBitMap == null) {
            return;
        }
        canvas.save();
        Log.e("onMeasure", "onDraw");
        paint.setTextSize(contentTextSize);
        paint.setColor(contentColor);
        paint.setStyle(Paint.Style.FILL);
        int lineHeight = (int) (paint.getFontMetrics().bottom - paint.getFontMetrics().top + 1);
        bitmapRect.left = 0;
        bitmapRect.top = 0;
        bitmapRect.right = (int) (flagBitMap.getWidth() * 1f / flagBitMap.getHeight() * lineHeight);
        bitmapRect.bottom = lineHeight;

        bitmapRectF.left = 0f;
        bitmapRectF.top = 0f;
        bitmapRectF.right = bitmapRect.right * 1F;
        bitmapRectF.bottom = bitmapRect.bottom * 1F;
        canvas.drawBitmap(flagBitMap, bitmapRect, bitmapRectF, paint);

        //可绘制文字的最大宽度
        float leftWidthSpace = getWidth() - (getPaddingLeft() + getPaddingRight()) - bitmapRectF.right - 1f - flagAndContentSpace;

        int length = content.length();
        int oneLineIndex = 0;
        for (int i = 0; i < length; i++) {
            if (paint.measureText(content.substring(0, i)) > leftWidthSpace) {
                oneLineIndex = i - 1;
                break;
            }
        }
        if (oneLineIndex == 0) {
            oneLineIndex = content.length();
        }
        float y = (bitmapRectF.bottom - lineHeight) / 2f + Math.abs(paint.getFontMetrics().top);
        if (maxLine == 1) {
            for (int i = 1; i <= endText.length(); i++) {
                if (paint.measureText(endText) > paint.measureText(content.substring(oneLineIndex - i, oneLineIndex))) {
                    oneLineIndex = oneLineIndex - i;
                    break;
                }
            }
            canvas.drawText(content.substring(0, oneLineIndex - 1) + endText, bitmapRectF.right + flagAndContentSpace, y, paint);
        } else {
            canvas.drawText(content.substring(0, oneLineIndex), bitmapRectF.right + flagAndContentSpace, y, paint);
            if (oneLineIndex < content.length()) {
                //说明有第二行
                canvas.save();
                canvas.translate(0f, bitmapRect.bottom);
                String leftContent = content.substring(oneLineIndex);
                textPaint.set(paint);
                StaticLayout staticLayout;
                if (Build.VERSION.SDK_INT <= 23) {
                    staticLayout = new StaticLayout(leftContent,
                            0, leftContent.length(), textPaint,
                            getWidth() - getPaddingLeft() - getPaddingRight(),
                            Layout.Alignment.ALIGN_NORMAL,
                            0f,
                            0f,
                            false,
                            TextUtils.TruncateAt.END,
                            0);
                    //由于设置最大行数的方法是@hide的所以反射修改值
                    try {
                        Field mMaxLines = staticLayout.getClass().getDeclaredField("mMaxLines");
                        mMaxLines.setAccessible(true);
                        mMaxLines.set(staticLayout, maxLine - 1);
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }

                } else {
                    staticLayout = StaticLayout.Builder.obtain(leftContent, 0, leftContent.length(), textPaint, getWidth() - getPaddingLeft() - getPaddingRight())
                            .setEllipsize(TextUtils.TruncateAt.END)
                            .setMaxLines(maxLine - 1)
                            .build();
                }
                staticLayout.draw(canvas);
                canvas.restore();
            }
        }
        canvas.restore();
    }
}