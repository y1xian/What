package com.yyxnb.util_other;

import android.content.ContentResolver;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import com.yyxnb.util_app.AppUtils;

/**
 * 亮度相关
 * {@link BrightnessUtils#isAutoBrightnessEnabled} : 判断是否开启自动调节亮度
 * {@link BrightnessUtils#setAutoBrightnessEnabled}: 设置是否开启自动调节亮度
 * {@link BrightnessUtils#getBrightness}           : 获取屏幕亮度
 * {@link BrightnessUtils#setBrightness}           : 设置屏幕亮度
 * {@link BrightnessUtils#setWindowBrightness}     : 设置窗口亮度
 * {@link BrightnessUtils#getWindowBrightness}     : 获取窗口亮度
 */
public final class BrightnessUtils {

    private BrightnessUtils() {
    }

    /**
     * 判断是否开启自动调节亮度.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAutoBrightnessEnabled() {
        try {
            int mode = Settings.System.getInt(
                    AppUtils.getApp().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE
            );
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置是否开启自动调节亮度.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param enabled True to enabled, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean setAutoBrightnessEnabled(final boolean enabled) {
        return Settings.System.putInt(
                AppUtils.getApp().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                        : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
    }

    /**
     * 获取屏幕亮度
     *
     * @return 屏幕亮度 0-255
     */
    public static int getBrightness() {
        try {
            return Settings.System.getInt(
                    AppUtils.getApp().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * 并得到授权
     *
     * @param brightness 亮度值
     */
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        ContentResolver resolver = AppUtils.getApp().getContentResolver();
        boolean b = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
        return b;
    }

    /**
     * 设置窗口亮度
     *
     * @param window     窗口
     * @param brightness 亮度值
     */
    public static void setWindowBrightness(@NonNull final Window window,
                                           @IntRange(from = 0, to = 255) final int brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * 获取窗口亮度
     *
     * @param window 窗口
     * @return 屏幕亮度 0-255
     */
    public static int getWindowBrightness(final Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) {
            return getBrightness();
        }
        return (int) (brightness * 255);
    }
}