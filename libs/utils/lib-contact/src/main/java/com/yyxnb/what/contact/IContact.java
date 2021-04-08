package com.yyxnb.what.contact;

import android.content.Context;
import android.net.Uri;

public interface IContact {

    /**
     * 申请通讯录权限
     *
     * @param context 上下文
     */
    void contactPermission(Context context);

    /**
     * 调起系统的联系人界面
     *
     * @param context     上下文
     * @param requestCode 请求code
     */
    void openContact(Context context, int requestCode);

    /**
     * 调起之后，点击联系人的回调
     *
     * @param context 上下文
     * @param uri     指定的联系人
     * @return [0] 名称 [1] 手机号，多手机号则以;隔开
     */
    String[] getContact(Context context, Uri uri);

    /**
     * 返回所有的联系人
     *
     * @param context 上下文
     * @return 姓名=手机号;
     */
    String[] getContacts(Context context);


}
