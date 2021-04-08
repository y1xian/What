package com.yyxnb.what.contact;

import android.content.Context;
import android.net.Uri;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/04/08
 * 描    述：通讯录帮助类
 * ================================================
 */
public class ContactHelper {

    private final static IContact HELPER = new ContactImpl();

    /**
     * 申请通讯录权限
     *
     * @param context 上下文
     */
    public static void contactPermission(Context context) {
        HELPER.contactPermission(context);
    }

    /**
     * 调起系统的联系人界面
     *
     * @param context     上下文
     * @param requestCode 请求code
     */
    public static void openContact(Context context, int requestCode) {
        HELPER.openContact(context, requestCode);
    }

    /**
     * 调起之后，点击联系人的回调
     *
     * @param context 上下文
     * @param uri     指定的联系人
     * @return [0] 名称 [1] 手机号，多手机号则以;隔开
     */
    public static String[] getContact(Context context, Uri uri) {
        return HELPER.getContact(context, uri);
    }

    /**
     * 返回所有的联系人
     *
     * @param context 上下文
     * @return 姓名=手机号;
     */
    public static String[] getContacts(Context context) {
        return HELPER.getContacts(context);
    }

}
