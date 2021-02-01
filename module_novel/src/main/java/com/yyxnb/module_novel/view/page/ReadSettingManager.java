package com.yyxnb.module_novel.view.page;

import com.yyxnb.util_cache.CacheUtils;

/**
 * Created by newbiechen on 17-5-17.
 * 阅读器的配置管理
 */

public class ReadSettingManager {
    /*************实在想不出什么好记的命名方式。。******************/
    public static final int READ_BG_DEFAULT = 0;
    public static final int READ_BG_1 = 1;
    public static final int READ_BG_2 = 2;
    public static final int READ_BG_3 = 3;
    public static final int READ_BG_4 = 4;
    public static final int NIGHT_MODE = 5;

    public static final String SHARED_READ_BG = "shared_read_bg";
    public static final String SHARED_READ_BRIGHTNESS = "shared_read_brightness";
    public static final String SHARED_READ_IS_BRIGHTNESS_AUTO = "shared_read_is_brightness_auto";
    public static final String SHARED_READ_TEXT_SIZE = "shared_read_text_size";
    public static final String SHARED_READ_IS_TEXT_DEFAULT = "shared_read_text_default";
    public static final String SHARED_READ_PAGE_MODE = "shared_read_mode";
    public static final String SHARED_READ_NIGHT_MODE = "shared_night_mode";
    public static final String SHARED_READ_VOLUME_TURN_PAGE = "shared_read_volume_turn_page";
    public static final String SHARED_READ_FULL_SCREEN = "shared_read_full_screen";
    public static final String SHARED_READ_CONVERT_TYPE = "shared_read_convert_type";

    private static volatile ReadSettingManager sInstance;

    public static ReadSettingManager getInstance() {
        if (sInstance == null) {
            synchronized (ReadSettingManager.class) {
                if (sInstance == null) {
                    sInstance = new ReadSettingManager();
                }
            }
        }
        return sInstance;
    }

    private ReadSettingManager() {
    }

    public void setPageStyle(PageStyle pageStyle) {
        CacheUtils.save(SHARED_READ_BG, pageStyle.ordinal());
    }

    public void setBrightness(int progress) {
        CacheUtils.save(SHARED_READ_BRIGHTNESS, progress);
    }

    public void setAutoBrightness(boolean isAuto) {
        CacheUtils.save(SHARED_READ_IS_BRIGHTNESS_AUTO, isAuto);
    }

    public void setDefaultTextSize(boolean isDefault) {
        CacheUtils.save(SHARED_READ_IS_TEXT_DEFAULT, isDefault);
    }

    public void setTextSize(int textSize) {
        CacheUtils.save(SHARED_READ_TEXT_SIZE, textSize);
    }

    public void setPageMode(PageMode mode) {
        CacheUtils.save(SHARED_READ_PAGE_MODE, mode.ordinal());
    }

    public void setNightMode(boolean isNight) {
        CacheUtils.save(SHARED_READ_NIGHT_MODE, isNight);
    }

    public int getBrightness() {
        return CacheUtils.get(SHARED_READ_BRIGHTNESS, 120);
    }

    public boolean isBrightnessAuto() {
        return CacheUtils.get(SHARED_READ_IS_BRIGHTNESS_AUTO, false);
    }

    public int getTextSize() {
        return CacheUtils.get(SHARED_READ_TEXT_SIZE, 24);
    }

    public boolean isDefaultTextSize() {
        return CacheUtils.get(SHARED_READ_IS_TEXT_DEFAULT, false);
    }

    public PageMode getPageMode() {
        int mode = CacheUtils.get(SHARED_READ_PAGE_MODE, PageMode.SIMULATION.ordinal());
        return PageMode.values()[mode];
    }

    public PageStyle getPageStyle() {
        int style = CacheUtils.get(SHARED_READ_BG, PageStyle.BG_0.ordinal());
        return PageStyle.values()[style];
    }

    public boolean isNightMode() {
        return CacheUtils.get(SHARED_READ_NIGHT_MODE, false);
    }

    public void setVolumeTurnPage(boolean isTurn) {
        CacheUtils.save(SHARED_READ_VOLUME_TURN_PAGE, isTurn);
    }

    public boolean isVolumeTurnPage() {
        return CacheUtils.get(SHARED_READ_VOLUME_TURN_PAGE, false);
    }

    public void setFullScreen(boolean isFullScreen) {
        CacheUtils.save(SHARED_READ_FULL_SCREEN, isFullScreen);
    }

    public boolean isFullScreen() {
        return CacheUtils.get(SHARED_READ_FULL_SCREEN, false);
    }

    public void setConvertType(int convertType) {
        CacheUtils.save(SHARED_READ_CONVERT_TYPE, convertType);
    }

    public int getConvertType() {
        return CacheUtils.get(SHARED_READ_CONVERT_TYPE, 0);
    }
}
