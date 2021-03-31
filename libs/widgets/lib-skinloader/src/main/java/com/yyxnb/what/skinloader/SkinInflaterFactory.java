package com.yyxnb.what.skinloader;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import com.yyxnb.what.skinloader.bean.SkinAttr;
import com.yyxnb.what.skinloader.bean.SkinConfig;
import com.yyxnb.what.skinloader.parser.SkinAttributeParser;
import com.yyxnb.what.skinloader.util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SkinInflaterFactory implements LayoutInflaterFactory {

    private static final String TAG = SkinInflaterFactory.class.getSimpleName();
    private Factory mViewCreateFactory;

    public static void setFactory(LayoutInflater inflater) {
//        setLayoutInflaterFactory(inflater);
//        inflater.setFactory(new SkinInflaterFactory());
    }

    /**
     * 干涉xml中view的创建，实现xml中资源换肤
     * 因此，在相关activity的onCreate()中setContentView()方法之前添加
     */
    public static void setFactory(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        SkinInflaterFactory factory = new SkinInflaterFactory();
        if (activity instanceof AppCompatActivity) {
            //AppCompatActivity本身包含一个factory,将TextView等转换为AppCompatTextView.java, 参考：AppCompatDelegateImplV9.java
            final AppCompatDelegate delegate = ((AppCompatActivity) activity).getDelegate();
            factory.setInterceptFactory((name, context, attrs) -> delegate.createView(null, name, context, attrs));
        }
        LayoutInflaterCompat.setFactory(inflater, factory);
    }

    //因为LayoutInflater的setFactory方法只能调用一次，当框架外需要处理view的创建时，可以调用此方法
    public void setInterceptFactory(Factory factory) {
        mViewCreateFactory = factory;
    }

    public static void setLayoutInflaterFactory(LayoutInflater original) {
        LayoutInflater layoutInflater = original;
        try {
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.set(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(View view, String name, Context context, AttributeSet attrs) {
        if (SkinConfig.DEBUG) {
            Log.d(TAG, "SkinInflaterFactory onCreateView(), create view name=" + name + "  ");
        }
//        View view = null;
        if (mViewCreateFactory != null) {
            //给框架外提供创建View的机会
            view = mViewCreateFactory.onCreateView(name, context, attrs);
        }
        if (isSupportSkin(attrs)) {
            if (view == null) {
                view = createView(context, name, attrs);
            }
            if (view != null) {
                parseAndSaveSkinAttr(attrs, view);
            }
        }

        return view;
    }

    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            assertInflaterContext(inflater, context);

            if (-1 == name.indexOf('.')) {
                if ("View".equals(name) || "ViewStub".equals(name) || "ViewGroup".equals(name)) {
                    view = inflater.createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(name, "android.webkit.", attrs);
                }
            } else {
                view = inflater.createView(name, null, attrs);
            }

        } catch (Exception ex) {
            Log.e(TAG, "createView(), create view failed", ex);
            view = null;
            ex.printStackTrace();
        }
        return view;
    }

    //只有在xml中设置了View的属性skin:enable，才支持xml属性换肤
    public boolean isSupportSkin(AttributeSet attrs) {
        return attrs.getAttributeBooleanValue(SkinConfig.SKIN_XML_NAMESPACE,
                SkinConfig.ATTR_SKIN_ENABLE, false);
    }

    //获取xml中指定的换肤属性，比如：skin:attrs = "textColor|background", 假如为空，表示支持所有能够支持的换肤属性
    private @Nullable
    String getXmlSpecifiedAttrs(@NonNull AttributeSet attrs) {
        return attrs.getAttributeValue(SkinConfig.SKIN_XML_NAMESPACE, SkinConfig.SUPPORTED_ATTR_SKIN_LIST);
    }

    private void parseAndSaveSkinAttr(AttributeSet attrs, View view) {
        String specifiedAttrs = getXmlSpecifiedAttrs(attrs);
        String[] specifiedAttrsList = null;
        if (specifiedAttrs != null && specifiedAttrs.trim().length() > 0) {
            specifiedAttrsList = specifiedAttrs.split("\\|");
        }
        HashMap<String, SkinAttr> viewAttrs = SkinAttributeParser.parseSkinAttr(attrs, view, specifiedAttrsList);

        if (viewAttrs == null || viewAttrs.size() == 0) {
            return;
        }

        //设置view的皮肤属性
        SkinManager.get().deployViewSkinAttrs(view, viewAttrs);
        //save view attribute
        SkinManager.get().saveSkinView(view, viewAttrs);
    }

    //在低版本系统中会出inflaterContext为空的问题， 因此需要处理inflaterContext为空的情况
    private void assertInflaterContext(LayoutInflater inflater, Context context) {
        Context inflaterContext = inflater.getContext();
        if (inflaterContext == null) {
            ReflectUtils.setField(inflater, "mContext", context);
        }


        //设置mConstructorArgs的第一个参数context
        Object[] constructorArgs = (Object[]) ReflectUtils.getField(inflater, "mConstructorArgs");
        if (null == constructorArgs || constructorArgs.length < 2) {
            //异常，一般不会发生
            constructorArgs = new Object[2];
            ReflectUtils.setField(inflater, "mConstructorArgs", constructorArgs);
        }

        //如果mConstructorArgs的第一个参数为空，则设置为mContext
        if (null == constructorArgs[0]) {
            constructorArgs[0] = inflater.getContext();
        }
    }

}
