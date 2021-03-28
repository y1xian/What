package com.yyxnb.what.view.titlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyxnb.what.core.DpUtils;
import com.yyxnb.what.view.R;


/**
 * 通用标题栏
 */
@SuppressWarnings("ResourceType")
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private View viewStatusBarFill;                     // 状态栏填充视图
    private View viewBottomLine;                        // 分隔线视图
    private RelativeLayout rlMain;                      // 主视图

    private TextView tvLeft;                            // 左边TextView
    private ImageButton btnLeft;                        // 左边ImageButton
    private View viewCustomLeft;
    private TextView tvRight;                           // 右边TextView
    private ImageButton btnRight;                       // 右边ImageButton
    private View viewCustomRight;
    private LinearLayout llMainCenter;
    private TextView tvCenter;                          // 标题栏文字
    private TextView tvCenterSub;                       // 副标题栏文字
    private ProgressBar progressCenter;                 // 中间进度条,默认隐藏
    private View centerCustomView;                      // 中间自定义视图

    private boolean fillStatusBar;                      // 是否撑起状态栏, true时,标题栏浸入状态栏
    private int statusBarColor;                         // 状态栏背景颜色
    private int titleBarColor;                          // 标题栏背景颜色
    private int titleBarHeight;                         // 标题栏高度

    private boolean showBottomLine;                     // 是否显示底部分割线
    private int bottomLineColor;                        // 分割线颜色

    private int leftType;                               // 左边视图类型
    private String leftText;                            // 左边TextView文字
    private int leftTextColor;                          // 左边TextView颜色
    private float leftTextSize;                         // 左边TextView文字大小
    private int leftDrawable;                           // 左边TextView drawableLeft资源
    private float leftDrawablePadding;                  // 左边TextView drawablePadding
    private int leftImageResource;                      // 左边图片资源
    private int leftCustomViewRes;                      // 左边自定义视图布局资源

    private int rightType;                              // 右边视图类型
    private String rightText;                           // 右边TextView文字
    private int rightTextColor;                         // 右边TextView颜色
    private float rightTextSize;                        // 右边TextView文字大小
    private int rightImageResource;                     // 右边图片资源
    private int rightCustomViewRes;                     // 右边自定义视图布局资源

    private int centerType;                             // 中间视图类型
    private String centerText;                          // 中间TextView文字
    private int centerTextColor;                        // 中间TextView字体颜色
    private float centerTextSize;                       // 中间TextView字体大小
    private boolean centerTextMarquee;                  // 中间TextView字体是否显示跑马灯效果
    private String centerSubText;                       // 中间subTextView文字
    private int centerSubTextColor;                     // 中间subTextView字体颜色
    private float centerSubTextSize;                    // 中间subTextView字体大小
    private int centerCustomViewRes;                    // 中间自定义布局资源

    private int PADDING_10;
    private int PADDING_12;

    private OnTitleBarListener listener;
    private OnLeftBarListener leftListener;
    private OnTitleBarDoubleClickListener doubleClickListener;

    private static final int TYPE_LEFT_NONE = 0;
    private static final int TYPE_LEFT_TEXTVIEW = 1;
    private static final int TYPE_LEFT_IMAGEBUTTON = 2;
    private static final int TYPE_LEFT_CUSTOM_VIEW = 3;

    private static final int TYPE_RIGHT_NONE = 0;
    private static final int TYPE_RIGHT_TEXTVIEW = 1;
    private static final int TYPE_RIGHT_IMAGEBUTTON = 2;
    private static final int TYPE_RIGHT_CUSTOM_VIEW = 3;

    private static final int TYPE_CENTER_NONE = 0;
    private static final int TYPE_CENTER_TEXTVIEW = 1;
    private static final int TYPE_CENTER_CUSTOM_VIEW = 3;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
        initGlobalViews(context);
        initMainViews(context);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        PADDING_10 = DpUtils.dp2px(context, 10);
        PADDING_12 = DpUtils.dp2px(context, 12);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        // notice 未引入沉浸式标题栏之前,隐藏标题栏撑起布局
        fillStatusBar = array.getBoolean(R.styleable.TitleBar_tb_fillStatusBar, true);
        statusBarColor = array.getColor(R.styleable.TitleBar_tb_statusBarColor, getResources().getColor(R.color.statusBar));

        titleBarColor = array.getColor(R.styleable.TitleBar_tb_titleBarColor, getResources().getColor(R.color.titleBar));
        titleBarHeight = (int) array.getDimension(R.styleable.TitleBar_tb_titleBarHeight, DpUtils.dp2px(context, 48));

        showBottomLine = array.getBoolean(R.styleable.TitleBar_tb_showBottomLine, true);
        bottomLineColor = array.getColor(R.styleable.TitleBar_tb_bottomLineColor, getResources().getColor(R.color.colorLine));

        leftType = array.getInt(R.styleable.TitleBar_tb_leftType, TYPE_LEFT_IMAGEBUTTON);
        if (leftType == TYPE_LEFT_TEXTVIEW) {
            leftText = array.getString(R.styleable.TitleBar_tb_leftText);
            leftTextColor = array.getColor(R.styleable.TitleBar_tb_leftTextColor, getResources().getColor(R.color.colorTitle));
            leftTextSize = array.getDimension(R.styleable.TitleBar_tb_leftTextSize, DpUtils.dp2px(context, 16));
            leftDrawable = array.getResourceId(R.styleable.TitleBar_tb_leftDrawable, 0);
            leftDrawablePadding = array.getDimension(R.styleable.TitleBar_tb_leftDrawablePadding, 5);
        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            leftImageResource = array.getResourceId(R.styleable.TitleBar_tb_leftImageResource, R.mipmap.ic_titlebar_back);
        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            leftCustomViewRes = array.getResourceId(R.styleable.TitleBar_tb_leftCustomView, 0);
        }

        rightType = array.getInt(R.styleable.TitleBar_tb_rightType, TYPE_RIGHT_NONE);
        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            rightText = array.getString(R.styleable.TitleBar_tb_rightText);
            rightTextColor = array.getColor(R.styleable.TitleBar_tb_rightTextColor, getResources().getColor(R.color.colorTitle));
            rightTextSize = array.getDimension(R.styleable.TitleBar_tb_rightTextSize, DpUtils.dp2px(context, 16));
        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            rightImageResource = array.getResourceId(R.styleable.TitleBar_tb_rightImageResource, 0);
        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            rightCustomViewRes = array.getResourceId(R.styleable.TitleBar_tb_rightCustomView, 0);
        }

        centerType = array.getInt(R.styleable.TitleBar_tb_centerType, TYPE_CENTER_TEXTVIEW);
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            centerText = array.getString(R.styleable.TitleBar_tb_centerText);
            centerTextColor = array.getColor(R.styleable.TitleBar_tb_centerTextColor, getResources().getColor(R.color.colorTitle));
            centerTextSize = array.getDimension(R.styleable.TitleBar_tb_centerTextSize, DpUtils.dp2px(context, 20));
            centerTextMarquee = array.getBoolean(R.styleable.TitleBar_tb_centerTextMarquee, true);
            centerSubText = array.getString(R.styleable.TitleBar_tb_centerSubText);
            centerSubTextColor = array.getColor(R.styleable.TitleBar_tb_centerSubTextColor, getResources().getColor(R.color.colorHint));
            centerSubTextSize = array.getDimension(R.styleable.TitleBar_tb_centerSubTextSize, DpUtils.dp2px(context, 12));
        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            centerCustomViewRes = array.getResourceId(R.styleable.TitleBar_tb_centerCustomView, 0);
        }

        array.recycle();
    }

    private final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 初始化全局视图
     *
     * @param context 上下文
     */
    private void initGlobalViews(Context context) {
        ViewGroup.LayoutParams globalParams = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setLayoutParams(globalParams);

        // 构建标题栏填充视图
        if (fillStatusBar) {
            int statusBarHeight = BarUtils.getStatusBarHeight(context);
            viewStatusBarFill = new View(context);
            viewStatusBarFill.setId(BarUtils.generateViewId());
            //状态栏颜色与标题栏颜色一致 非渐变
            viewStatusBarFill.setBackgroundColor(statusBarColor);
            LayoutParams statusBarParams = new LayoutParams(MATCH_PARENT, statusBarHeight);
            statusBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            addView(viewStatusBarFill, statusBarParams);
        }

        // 构建主视图
        rlMain = new RelativeLayout(context);
        rlMain.setId(BarUtils.generateViewId());
        rlMain.setBackgroundColor(titleBarColor);
        LayoutParams mainParams = new LayoutParams(MATCH_PARENT, titleBarHeight);
        if (fillStatusBar) {
            mainParams.addRule(RelativeLayout.BELOW, viewStatusBarFill.getId());
        } else {
            mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }

        // 计算主布局高度
        if (showBottomLine) {
            mainParams.height = titleBarHeight - Math.max(1, DpUtils.dp2px(context, 0.1f));
        } else {
            mainParams.height = titleBarHeight;
        }
        addView(rlMain, mainParams);

        // 构建分割线视图
        if (showBottomLine) {
            // 已设置显示标题栏分隔线,5.0以下机型,显示分隔线
            viewBottomLine = new View(context);
            viewBottomLine.setBackgroundColor(bottomLineColor);
            LayoutParams bottomLineParams = new LayoutParams(MATCH_PARENT, Math.max(1, DpUtils.dp2px(context, 0.1f)));
            bottomLineParams.addRule(RelativeLayout.BELOW, rlMain.getId());

            addView(viewBottomLine, bottomLineParams);
        }
    }

    /**
     * 初始化主视图
     *
     * @param context 上下文
     */
    private void initMainViews(Context context) {
        if (leftType != TYPE_LEFT_NONE) {
            initMainLeftViews(context);
        }
        if (rightType != TYPE_RIGHT_NONE) {
            initMainRightViews(context);
        }
        if (centerType != TYPE_CENTER_NONE) {
            initMainCenterViews(context);
        }
    }

    /**
     * 初始化主视图左边部分
     * -- add: adaptive RTL
     *
     * @param context 上下文
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initMainLeftViews(Context context) {
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (leftType == TYPE_LEFT_TEXTVIEW) {
            // 初始化左边TextView
            tvLeft = new TextView(context);
            tvLeft.setId(BarUtils.generateViewId());
            tvLeft.setText(leftText);
            tvLeft.setTextColor(leftTextColor);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            tvLeft.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvLeft.setSingleLine(true);
            tvLeft.setOnClickListener(this);
            // 设置DrawableLeft及DrawablePadding
            if (leftDrawable != 0) {
                tvLeft.setCompoundDrawablePadding((int) leftDrawablePadding);
                tvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, 0, 0, 0);
            }
            tvLeft.setPadding(PADDING_12, 0, PADDING_12, 0);

            rlMain.addView(tvLeft, leftInnerParams);

        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            // 初始化左边ImageButton
            btnLeft = new ImageButton(context);
            btnLeft.setId(BarUtils.generateViewId());
            btnLeft.setBackgroundColor(Color.TRANSPARENT);
            btnLeft.setImageResource(leftImageResource);
            btnLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnLeft.setPadding(PADDING_10, 0, PADDING_10, 0);
            btnLeft.setOnClickListener(this);

            rlMain.addView(btnLeft, leftInnerParams);

        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            // 初始化自定义View
            viewCustomLeft = LayoutInflater.from(context).inflate(leftCustomViewRes, rlMain, false);
            if (viewCustomLeft.getId() == View.NO_ID) {
                viewCustomLeft.setId(BarUtils.generateViewId());
            }
            rlMain.addView(viewCustomLeft, leftInnerParams);
        }
    }

    /**
     * 初始化主视图右边部分
     * -- add: adaptive RTL
     *
     * @param context 上下文
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initMainRightViews(Context context) {
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            // 初始化右边TextView
            tvRight = new TextView(context);
            tvRight.setId(BarUtils.generateViewId());
            tvRight.setText(rightText);
            tvRight.setTextColor(rightTextColor);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
            tvRight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            tvRight.setSingleLine(true);
            tvRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            tvRight.setOnClickListener(this);
            rlMain.addView(tvRight, rightInnerParams);

        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            // 初始化右边ImageBtn
            btnRight = new ImageButton(context);
            btnRight.setId(BarUtils.generateViewId());
            btnRight.setImageResource(rightImageResource);
            btnRight.setBackgroundColor(Color.TRANSPARENT);
            btnRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnRight.setPadding(PADDING_10, 0, PADDING_10, 0);
            btnRight.setOnClickListener(this);

            rlMain.addView(btnRight, rightInnerParams);

        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            // 初始化自定义view
            viewCustomRight = LayoutInflater.from(context).inflate(rightCustomViewRes, rlMain, false);
            if (viewCustomRight.getId() == View.NO_ID) {
                viewCustomRight.setId(BarUtils.generateViewId());
            }
            rlMain.addView(viewCustomRight, rightInnerParams);
        }
    }

    /**
     * 初始化主视图中间部分
     *
     * @param context 上下文
     */
    private void initMainCenterViews(Context context) {
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            // 初始化中间子布局
            llMainCenter = new LinearLayout(context);
            llMainCenter.setId(BarUtils.generateViewId());
            llMainCenter.setGravity(Gravity.CENTER);
            llMainCenter.setOrientation(LinearLayout.VERTICAL);
            llMainCenter.setOnClickListener(this);

            LayoutParams centerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerParams.setMarginStart(PADDING_12);
            centerParams.setMarginEnd(PADDING_12);
            centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(llMainCenter, centerParams);

            // 初始化标题栏TextView
            tvCenter = new TextView(context);
            tvCenter.setText(centerText);
            tvCenter.setTextColor(centerTextColor);
            tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextSize);
            tvCenter.setGravity(Gravity.CENTER);
            tvCenter.setSingleLine(true);
            // 设置跑马灯效果
            //最大宽度 设置此行会导致xml无法预览 改为标题栏高度 * 4
//            tvCenter.setMaxWidth((int) (BarUtils.getScreenPixelSize(context)[0] * 3 / 5.0));
            tvCenter.setMaxWidth(titleBarHeight * 4);
            if (centerTextMarquee) {
                tvCenter.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tvCenter.setMarqueeRepeatLimit(-1);
                tvCenter.requestFocus();
                tvCenter.setSelected(true);
            }

            LinearLayout.LayoutParams centerTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenter, centerTextParams);

            // 初始化进度条, 显示于标题栏左边
            progressCenter = new ProgressBar(context);
            progressCenter.setIndeterminateDrawable(getResources().getDrawable(R.drawable._tb_progressbar));
            progressCenter.setVisibility(View.GONE);
            int progressWidth = DpUtils.dp2px(context, 18);
            LayoutParams progressParams = new LayoutParams(progressWidth, progressWidth);
            progressParams.addRule(RelativeLayout.CENTER_VERTICAL);
            progressParams.addRule(RelativeLayout.START_OF, llMainCenter.getId());
            rlMain.addView(progressCenter, progressParams);

            // 初始化副标题栏
            tvCenterSub = new TextView(context);
            tvCenterSub.setText(centerSubText);
            tvCenterSub.setTextColor(centerSubTextColor);
            tvCenterSub.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerSubTextSize);
            tvCenterSub.setGravity(Gravity.CENTER);
            tvCenterSub.setSingleLine(true);
            if (TextUtils.isEmpty(centerSubText)) {
                tvCenterSub.setVisibility(View.GONE);
            }

            LinearLayout.LayoutParams centerSubTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenterSub, centerSubTextParams);

        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            // 初始化中间自定义布局
            centerCustomView = LayoutInflater.from(context).inflate(centerCustomViewRes, rlMain, false);
            if (centerCustomView.getId() == View.NO_ID) {
                centerCustomView.setId(BarUtils.generateViewId());
            }
            LayoutParams centerCustomParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerCustomParams.setMarginStart(PADDING_12);
            centerCustomParams.setMarginEnd(PADDING_12);
            centerCustomParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(centerCustomView, centerCustomParams);
        }
    }


    private long lastClickMillis = 0;     // 双击事件中，上次被点击时间

    @Override
    public void onClick(View v) {
        if (leftListener != null && (v.equals(btnLeft) || v.equals(tvLeft))) {
            leftListener.onClicked(v);
            return;
        } else if (listener == null) {
            return;
        }

        if (v.equals(llMainCenter) && doubleClickListener != null) {
            long currentClickMillis = System.currentTimeMillis();
            if (currentClickMillis - lastClickMillis < 500) {
                doubleClickListener.onClicked(v);
            }
            lastClickMillis = currentClickMillis;
        } else if (v.equals(tvLeft)) {
            listener.onClicked(v, ACTION_LEFT_TEXT);
        } else if (v.equals(btnLeft)) {
            listener.onClicked(v, ACTION_LEFT_BUTTON);
        } else if (v.equals(tvRight)) {
            listener.onClicked(v, ACTION_RIGHT_TEXT);
        } else if (v.equals(btnRight)) {
            listener.onClicked(v, ACTION_RIGHT_BUTTON);
        } else if (v.equals(tvCenter)) {
            listener.onClicked(v, ACTION_CENTER_TEXT);
        }
    }

//    public void setFillStatusBar(boolean fillStatusBar) {
//        this.fillStatusBar = fillStatusBar;
//    }

    /**
     * 设置背景颜色
     */
    @Override
    public void setBackgroundColor(int color) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setBackgroundColor(color);
        }
        rlMain.setBackgroundColor(color);
    }

    /**
     * 设置背景图片、渐变色 (整体背景 包括状态栏)
     */
    @Override
    public void setBackgroundResource(int resource) {
        setBackgroundColor(Color.TRANSPARENT);
        super.setBackgroundResource(resource);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int color) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setBackgroundColor(color);
        }
    }

    /**
     * 是否填充状态栏
     */
    public void showStatusBar(boolean show) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 获取标题栏底部横线
     */
    public View getButtomLine() {
        return viewBottomLine;
    }

    /**
     * 获取标题栏左边TextView，对应leftType = textView
     */
    public TextView getLeftTextView() {
        return tvLeft;
    }

    /**
     * 获取标题栏左边ImageButton，对应leftType = imageButton
     */
    public ImageButton getLeftImageButton() {
        return btnLeft;
    }

    /**
     * 获取标题栏右边TextView，对应rightType = textView
     */
    public TextView getRightTextView() {
        return tvRight;
    }

    /**
     * 获取标题栏右边ImageButton，对应rightType = imageButton
     */
    public ImageButton getRightImageButton() {
        return btnRight;
    }

    public LinearLayout getCenterLayout() {
        return llMainCenter;
    }

    /**
     * 获取标题栏中间TextView，对应centerType = textView
     */
    public TextView getCenterTextView() {
        return tvCenter;
    }

    public TextView getCenterSubTextView() {
        return tvCenterSub;
    }

    /**
     * 获取左边自定义布局
     */
    public View getLeftCustomView() {
        return viewCustomLeft;
    }

    /**
     * 获取右边自定义布局
     */
    public View getRightCustomView() {
        return viewCustomRight;
    }

    /**
     * 获取中间自定义布局视图
     */
    public View getCenterCustomView() {
        return centerCustomView;
    }

    /**
     * 左边自定义
     *
     * @param leftView
     */
    public void setLeftView(View leftView) {
        if (leftView.getId() == View.NO_ID) {
            leftView.setId(BarUtils.generateViewId());
        }
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(leftView, leftInnerParams);
    }

    /**
     * 中间自定义
     *
     * @param centerView
     */
    public void setCenterView(View centerView) {
        if (centerView.getId() == View.NO_ID) {
            centerView.setId(BarUtils.generateViewId());
        }
        LayoutParams centerInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(centerView, centerInnerParams);
    }

    /**
     * 右边自定义
     *
     * @param rightView
     */
    public void setRightView(View rightView) {
        if (rightView.getId() == View.NO_ID) {
            rightView.setId(BarUtils.generateViewId());
        }
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(rightView, rightInnerParams);
    }

    /**
     * 显示中间进度条
     */
    public void showCenterProgress() {
        progressCenter.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏中间进度条
     */
    public void dismissCenterProgress() {
        progressCenter.setVisibility(View.GONE);
    }

    /**
     * 左边返回按钮
     */
    public void setBackListener(OnLeftBarListener leftListener) {
        this.leftListener = leftListener;
    }

    /**
     * 设置点击事件监听
     */
    public void setClickListener(OnTitleBarListener listener) {
        this.listener = listener;
    }

    /**
     * 设置双击监听
     */
    public void setDoubleClickListener(OnTitleBarDoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    public static final int ACTION_LEFT_TEXT = 1;        // 左边TextView被点击
    public static final int ACTION_LEFT_BUTTON = 2;      // 左边ImageBtn被点击
    public static final int ACTION_RIGHT_TEXT = 3;       // 右边TextView被点击
    public static final int ACTION_RIGHT_BUTTON = 4;     // 右边ImageBtn被点击
    public static final int ACTION_CENTER_TEXT = 9;     // 中间文字点击

    /**
     * 返回事件
     */
    public interface OnLeftBarListener {
        /**
         * @param v
         */
        void onClicked(View v);
    }

    /**
     * 点击事件
     */
    public interface OnTitleBarListener {
        /**
         * @param v
         * @param action 对应ACTION_XXX, 如ACTION_LEFT_TEXT
         */
        void onClicked(View v, int action);
    }

    /**
     * 标题栏双击事件监听
     */
    public interface OnTitleBarDoubleClickListener {
        void onClicked(View v);
    }
}