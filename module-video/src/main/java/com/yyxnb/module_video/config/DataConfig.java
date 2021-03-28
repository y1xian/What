package com.yyxnb.module_video.config;

import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.what.okhttp.utils.GsonUtils;
import com.yyxnb.what.file.FileUtils;
import com.yyxnb.what.app.AppUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class DataConfig {

    private volatile static List<TikTokBean> tikTokBeans;

    /**
     * 首页数据
     *
     * @return
     */
    public static List<TikTokBean> getTikTokBeans() {
        if (tikTokBeans == null) {
            String content = FileUtils.parseFile(AppUtils.getApp(), "tiktok_data.json");
            tikTokBeans = GsonUtils.jsonToList(content, TikTokBean.class);
        }
        Collections.shuffle(tikTokBeans);
        return tikTokBeans;
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
                if (!v.equals("0")) {
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
