package com.yyxnb.module_music.config;

import com.yyxnb.what.app.AppUtils;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.what.file.FileUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class DataConfig {

    private volatile static List<MusicBean> musicBeans;

    /**
     * 数据
     *
     * @return
     */
    public static List<MusicBean> getMusicBeans() {
        if (musicBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "music.json");
            musicBeans = GsonUtils.jsonToList(content, MusicBean.class);
        }
        Collections.shuffle(musicBeans);
        return musicBeans;
    }

    public static String formatNum(int num) {
        final StringBuilder sb = new StringBuilder();

        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String nuit = "";

//        // 以千为单位处理
//            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
//                return "999+";
//            }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "w";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2).toString();
            nuit = "e";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!"0".equals(v)) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0) {
            return "0";
        }
        return sb.toString();
    }

}
