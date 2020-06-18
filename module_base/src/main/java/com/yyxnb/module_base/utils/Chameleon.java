package com.yyxnb.module_base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;

import com.yyxnb.common.AppConfig;

public class Chameleon {

//    private volatile static Chameleon chameleon;
//
//    public static Chameleon getInstance() {
//        if (chameleon == null){
//            synchronized (Chameleon.class){
//                if (chameleon == null){
//                    chameleon = new Chameleon();
//                }
//            }
//        }
//        return chameleon;
//    }


    private final static String SHARED_PREFERENCES_NAME = "chameleon";
    private final static String SHARED_PREFERENCES_KEY = "current_theme_id";

    private Context mContext = AppConfig.getInstance().getContext();
    private int themeId;

//    public Chameleon(Context mContext, int themeId) {
//        this.mContext = mContext;
//        this.themeId = themeId;
//    }

    public Chameleon(int themeId) {
        this.themeId = themeId;
    }

    private final int getColor(int attrId) {
        TypedValue value = new TypedValue();
        this.theme.resolveAttribute(attrId, value, true);
        return value.data;
    }

    public final Chameleon setTintColor(int imageViewId, int attrId) {
        this.tintColors.put(imageViewId, this.getColor(attrId));
        return this;
    }

    public final Chameleon setTextColor(int textViewId, int attrId) {
        this.textColors.put(textViewId, this.getColor(attrId));
        return this;
    }

    public final Chameleon setBackgroundColor(int viewId, int attrId) {
        this.backgroundColors.put(viewId, this.getColor(attrId));
        return this;
    }

    public final Chameleon setBackgroundTintColor(int viewId, int attrId) {
        this.backgroundTintColors.put(viewId, this.getColor(attrId));
        return this;
    }

//    public final Chameleon setColorByReflection(int viewId, String methodName, int attrId) {
//        Intrinsics.checkParameterIsNotNull(methodName, "methodName");
//        this.reflectionColors.put(viewId, TuplesKt.to(methodName, this.getColor(attrId)));
//        return this;
//    }
//
//    public final Chameleon setColorByProcessor(int viewId, int attrId,  Function2 processor) {
//        Intrinsics.checkParameterIsNotNull(processor, "processor");
//        this.processorColors.put(viewId, TuplesKt.to(this.getColor(attrId), processor));
//        return this;
//    }

    public void setTheme(Activity activity){
//        activity.setTheme(activity.getC);
    }

    private Resources.Theme theme = mContext.getResources().newTheme();

    private SparseIntArray titleColors;
    private SparseIntArray textColors;
    private SparseIntArray tintColors;
    private SparseIntArray backgroundColors;
    private SparseIntArray backgroundTintColors;
    private SparseArray<Pair<String, Integer>> reflectionColors;
    private SparseArray processorColors;
//    private SparseArray<Pair<Integer, ColorProcessor>> processorColors;


}
