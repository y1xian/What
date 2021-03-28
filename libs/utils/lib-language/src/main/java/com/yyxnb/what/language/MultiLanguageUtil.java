package com.yyxnb.what.language;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yyxnb.what.cache.KvUtils;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 * http://blog.csdn.net/finddreams
 */
public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";
    @SuppressLint("StaticFieldLeak")
    private volatile static MultiLanguageUtil instance;
    private Context mContext;
    public static final String SAVE_LANGUAGE = "save_language";

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil first");
        }
        return instance;
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        configuration.setLocale(targetLocale);
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        //语言更换生效的代码!
        resources.updateConfiguration(configuration, dm);
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     *
     * @return
     */
    private Locale getLanguageLocale() {
        String languageType = KvUtils.get(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_system.getLanguage());
        if (languageType.equals(LanguageType.LANGUAGE_system.getLanguage())) {
            return getSysLocale();
        } else if (languageType.equals(LanguageType.LANGUAGE_en.getLanguage())) {
            return Locale.ENGLISH;
        } else if (languageType.equals(LanguageType.LANGUAGE_zh.getLanguage())) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType.equals(LanguageType.LANGUAGE_zh_tw.getLanguage())) {
            return Locale.TRADITIONAL_CHINESE;
        }
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(int languageType) {
        KvUtils.save(MultiLanguageUtil.SAVE_LANGUAGE, languageType);
        MultiLanguageUtil.getInstance().setConfiguration();
    }

    public String getLanguageName() {
        String languageType = KvUtils.get(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_system.getLanguage());
        if (languageType.equals(LanguageType.LANGUAGE_en.getLanguage())) {
            return mContext.getString(R.string.setting_language_english);
        } else if (languageType.equals(LanguageType.LANGUAGE_zh.getLanguage())) {
            return mContext.getString(R.string.setting_simplified_chinese);
        } else if (languageType.equals(LanguageType.LANGUAGE_zh_tw.getLanguage())) {
            return mContext.getString(R.string.setting_traditional_chinese);
        }
        return mContext.getString(R.string.setting_language_auto);
    }

    /**
     * 获取到用户保存的语言类型
     *
     * @return
     */
    public String getLanguageType() {
        String languageType = KvUtils.get(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_system.getLanguage());
        if (languageType.equals(LanguageType.LANGUAGE_zh.getLanguage())) {
            return LanguageType.LANGUAGE_zh.getLanguage();
        } else if (languageType.equals(LanguageType.LANGUAGE_zh_tw.getLanguage())) {
            return LanguageType.LANGUAGE_zh_tw.getLanguage();
        } else if (languageType.equals(LanguageType.LANGUAGE_en.getLanguage())) {
            return LanguageType.LANGUAGE_en.getLanguage();
        }
        Log.e(TAG, "getLanguageType" + languageType);
        return languageType;
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
