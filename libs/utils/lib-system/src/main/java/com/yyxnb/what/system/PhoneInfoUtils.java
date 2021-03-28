package com.yyxnb.what.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.yyxnb.what.app.AppUtils;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/10/20
 * 历    史：
 * 描    述：获取手机号
 * 并不是所有的SIM卡都能获取到手机号码，只是有一部分可以拿到。这个是由于运营商没有把手机号码的数据写入到SIM卡中，
 * 能够读取SIM卡号的有个前提，那就是SIM卡已经写入了本机号码，不然是无法读取的
 * ================================================
 */
public class PhoneInfoUtils {


    private static String TAG = "PhoneInfoUtils";

    private static TelephonyManager telephonyManager;
    //移动运营商编号
    private static String NetworkOperator;

    public PhoneInfoUtils() {
        telephonyManager = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
    }

    //获取sim卡iccid
    @SuppressLint("MissingPermission")
    public static String getIccid() {
        String iccid = "N/A";
        iccid = telephonyManager.getSimSerialNumber();
        return iccid;
    }

    //获取电话号码
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Deprecated
    public static String getNativePhoneNumber() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String nativePhoneNumber = "N/A";
        nativePhoneNumber = tm.getLine1Number();
        return nativePhoneNumber;
    }

    //获取手机服务商信息
    public static String getProvidersName() {
        String providersName = "N/A";
        NetworkOperator = telephonyManager.getNetworkOperator();
        //IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        //Flog.d(TAG,"NetworkOperator="   NetworkOperator);
        if (NetworkOperator.equals("46000") || NetworkOperator.equals("46002")) {
            providersName = "中国移动";//中国移动
        } else if (NetworkOperator.equals("46001")) {
            providersName = "中国联通";//中国联通
        } else if (NetworkOperator.equals("46003")) {
            providersName = "中国电信";//中国电信
        }
        return providersName;

    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getPhoneInfo() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer sb = new StringBuffer();

        sb.append(" \nLine1Number = " + tm.getLine1Number());
        sb.append(" \nNetworkOperator = " + tm.getNetworkOperator());//移动运营商编号
        sb.append(" \nNetworkOperatorName = " + tm.getNetworkOperatorName());//移动运营商名称
        sb.append(" \nSimCountryIso = " + tm.getSimCountryIso());
        sb.append(" \nSimOperator = " + tm.getSimOperator());
        sb.append(" \nSimOperatorName = " + tm.getSimOperatorName());
        sb.append(" \nSimSerialNumber = " + tm.getSimSerialNumber());
        sb.append(" \nSubscriberId(IMSI) = " + tm.getSubscriberId());
        return sb.toString();
    }

}